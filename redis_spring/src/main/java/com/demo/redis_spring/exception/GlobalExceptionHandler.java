package com.demo.redis_spring.exception;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.config
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 5:46
 * @modifyed :
 * @description :
 **/

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Bad Request");
        errorDetails.put("message", ex.getMessage()); // 예외 메시지 사용

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}