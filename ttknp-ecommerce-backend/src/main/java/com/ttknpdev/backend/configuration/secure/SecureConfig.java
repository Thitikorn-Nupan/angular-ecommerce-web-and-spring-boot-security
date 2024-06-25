package com.ttknpdev.backend.configuration.secure;

import com.ttknpdev.backend.configuration.jwt.JwtAuthenticationEntryPoint;
import com.ttknpdev.backend.configuration.jwt.JwtRequestFilter;
import com.ttknpdev.backend.services.secure.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
    It's my config
    but, I don't need to specify it
    because I will call it
    pass by @bean -> SecurityConfig class -> SecurityFilterChain method
*/
@Configuration
@EnableWebSecurity
public class SecureConfig {
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private PasswordEncoder passwordEncoder;
    private JwtRequestFilter jwtRequestFilter;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public SecureConfig(
            @Qualifier("entryPoint") JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            @Qualifier("requestFilter") JwtRequestFilter jwtRequestFilter,
            @Qualifier("detailsService") JwtUserDetailsService jwtUserDetailsService,
            @Qualifier("bcryptEncoder") PasswordEncoder passwordEncoder  ) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load user for matching credentials. Use BCryptPasswordEncoder
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config
                .getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authenticate) ->
                        authenticate
                                /*
                                    If you’ve enabled Spring Security in your Spring Boot application,
                                    you will not be able to access the H2 database console.
                                    With its default settings under Spring Boot,
                                    Spring Security will block access to H2 database console.
                                    (don't forget to change h2-console default path)
                                */
                                // it won't authenticate two particular request
                                .requestMatchers("/api/province/**").permitAll()
                                .requestMatchers("/api/district/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/product-category").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/product/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/checkout/purchase").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/checkout/create-payment").permitAll()
                                //
                                .requestMatchers(HttpMethod.POST,"/api/sign-in/jwt").permitAll()
                                // it will authenticate this particular request who has Role like below it can access
                                // use jwt and role is a user can access this
                                .requestMatchers(HttpMethod.GET,"/api/order/search/**").hasAnyRole("USER")
                                // and all other requests need to be authenticated
                                .anyRequest()
                                .authenticated()
                                // *** we can pass user , password on header
                                // Basic <Base64 encoded username:password> (value) in Authorization key
                                // encode to base64 by www.base64encode.org
                )
                // make sure we use stateless session;
                // session won't be used to
                // store user's state.
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);
        // httpSecurity.addFilterBefore(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf().disable();
        httpSecurity.httpBasic();
        httpSecurity.headers().frameOptions().disable();
        /*
           when you need to use console of h2 you just specify  httpSecurity.headers().frameOptions().disable(); it works for case
           ** i try to access it can into login page but h2 page doesn't load
         */
        return httpSecurity.build();
    }
}
