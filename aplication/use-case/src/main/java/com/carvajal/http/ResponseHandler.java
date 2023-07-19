package com.carvajal.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static <T> ResponseEntity<?> generateResponse(HttpStatus status, String message, T data) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", status.value());
        responseBody.put("message", message);
        responseBody.put("data", data);
        return new ResponseEntity<>(responseBody, status);
    }

    public static <T> ResponseEntity<?> success(String message, T data, HttpStatus status) {
        return generateResponse(status, message, data);
    }

    public static <T> ResponseEntity<?> success(String message, T data) {
        return generateResponse(HttpStatus.OK, message, data);
    }

    public static <T> ResponseEntity<?> success(HttpStatus status, String message) {
        return generateResponse(status, message, null);
    }

    public static ResponseEntity<?> success(String message) {
        return success(message, null);
    }

    public static ResponseEntity<?> error(String message, HttpStatus status) {
        return generateResponse(status, message, null);
    }

    public static ResponseEntity<?> error(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
