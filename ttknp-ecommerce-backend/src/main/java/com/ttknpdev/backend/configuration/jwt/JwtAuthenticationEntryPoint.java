package com.ttknpdev.backend.configuration.jwt;

import com.ttknpdev.backend.log.Logback;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private Logback logback;

    public JwtAuthenticationEntryPoint() {
        this.logback = new Logback(JwtAuthenticationEntryPoint.class);
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        /*
            request - that resulted in an AuthenticationException
            response - so that the user agent can begin authentication
            authException - that caused the invocation
        */
        logback.log.warn("\n***commence() override method works (rejects every unauthenticated request)\n***Maybe, you are not invalid role");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized in this secure API");
    }
}
