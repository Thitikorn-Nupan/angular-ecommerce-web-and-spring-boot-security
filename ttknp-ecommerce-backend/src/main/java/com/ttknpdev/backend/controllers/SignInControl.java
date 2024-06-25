package com.ttknpdev.backend.controllers;

import com.ttknpdev.backend.configuration.jwt.JwtTokenUtil;
import com.ttknpdev.backend.services.secure.UserService;
import com.ttknpdev.backend.entities.sign_in_and_jwt.JwtResponse;
import com.ttknpdev.backend.entities.sign_in_and_jwt.SignIn;
import com.ttknpdev.backend.services.secure.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/sign-in")
public class SignInControl {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;
    private UserService userService;
    @Autowired
    public SignInControl(AuthenticationManager authenticationManager,
                     @Qualifier("tokenUtil") JwtTokenUtil jwtTokenUtil,
                     @Qualifier("detailsService") JwtUserDetailsService userDetailsService,
                     UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping(value = "/jwt")
    public ResponseEntity<JwtResponse> createJwtAndAuthenticateUser(@RequestBody SignIn signIn) throws Exception {
        authenticate(signIn.getUsername(),signIn.getPassword());

        final UserDetails USER_DETAILS = userDetailsService.loadUserByUsername(signIn.getUsername()); // retrieve the user from database

        final String TOKEN = jwtTokenUtil.generateToken(USER_DETAILS);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new JwtResponse(TOKEN,signIn.getUsername()));
    }
    /*
        {
            "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGl0aWtvcm4tbkBybXV0cC5hYy50aCIsImV4cCI6MTcyMjcwMzY5OCwiaWF0IjoxNzE5MTAzNjk4fQ.thXyDJ_htmOOWmjKfkr37Dm6ZHmFwXmlyvNMa3WkUP2nMnKiBU8JLFieYLthYgaj5tw1h2L-Q5zvJwLhnwmI2Q",
            "username": "thitikorn-n@rmutp.ac.th"
        }
    */

    private void authenticate(String username, String password) throws Exception {

        try {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        } catch (DisabledException e) {

            throw new Exception("User disabled", e.getCause());

        } catch (BadCredentialsException e) {
            /* If username and password is not correct it will find this exception */
            throw new Exception("Invalid credentials", e.getCause());

        }
    }
}
