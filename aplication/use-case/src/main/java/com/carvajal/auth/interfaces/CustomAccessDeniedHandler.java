package com.carvajal.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message = "Acceso denegado. No tienes permiso para acceder a este recurso.";
        String body = "{" +
                "\"data\": null," +
                "\"message\": \"" + message + "\"," +
                "\"status\": " + HttpServletResponse.SC_FORBIDDEN + "," +
                "}";
        logger.warn("FORBIDDEN: {} ", message);
        response.getWriter().write(body);
    }
}