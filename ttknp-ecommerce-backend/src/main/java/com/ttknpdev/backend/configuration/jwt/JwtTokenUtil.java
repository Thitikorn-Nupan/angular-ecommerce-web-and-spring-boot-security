package com.ttknpdev.backend.configuration.jwt;

import com.ttknpdev.backend.log.Logback;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {

    private final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000; // 1 hour
    @Value("${JWT.SECRET}")
    private String secret;
    private Logback logback;
    public JwtTokenUtil() {
        logback = new Logback(JwtTokenUtil.class);
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        logback.log.info("validateToken() works");
        final String username = getUsernameFromToken(token);
        if (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            logback.log.info("User exists and token doesn't expire");
            return true;
        } else {
            logback.log.warn("User did not exists");
            return false;
        }
    }

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // **
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver
                .apply(claims);
    }

    // retrieve any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        logback.log.info("getAllClaimsFromToken() works");
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    // check if the token has expired (v. หมดอายุแล้ว)
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration
                .before(new Date());
    }

    // retrieve expiration(การหมดอายุ) date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        logback.log.info("generateToken() works");
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(
                claims,
                userDetails.getUsername()
        );
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
       /*
           Here, the doGenerateToken() method creates a JSON Web Token
        */
        logback.log.info("doGenerateToken() works");
        // issue (v. ออก)
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject) // Subject is combination of the username
                .setIssuedAt(new Date()) // The token is issued at the current date and time
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // The token should expire after 24 hours
                .signWith(SignatureAlgorithm.HS512, secret) // The token is signed using a secret key, which you can specify in the application.properties file or from system environment variable
                .compact();
    }

}
