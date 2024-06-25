package com.ttknpdev.backend.configuration;

import com.ttknpdev.backend.configuration.jwt.JwtAuthenticationEntryPoint;
import com.ttknpdev.backend.configuration.jwt.JwtRequestFilter;
import com.ttknpdev.backend.configuration.jwt.JwtTokenUtil;
import com.ttknpdev.backend.services.secure.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfiguration {
    @Bean(name = "entryPoint")
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
    @Bean(name = "requestFilter")
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtUserDetailsService(),jwtTokenUtil());
    }
    @Bean(name = "tokenUtil")
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
    @Bean(name = "detailsService") // use @Bean instead @Service
    public JwtUserDetailsService jwtUserDetailsService() {
        return new JwtUserDetailsService();
    }
    @Bean(name = "bcryptEncoder")
    public PasswordEncoder getBcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
