package com.demo.redis_spring.exception;

import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.config
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 5:46
 * @modifyed :
 * @description :
 **/

/**
 * 애플리케이션에서 발생하는 전역 예외를 처리하는 클래스입니다.
 * 클라이언트에게 일관된 형식의 에러 응답을 제공합니다.
 */
@ControllerAdvice // 모든 컨트롤러에 걸쳐 예외를 처리하도록 선언
@Slf4j // Lombok을 사용하여 로거 자동 생성
public class GlobalExceptionHandler {

    /**
     * IllegalArgumentException (예: 유효하지 않은 요청 파라미터, 지원하지 않는 타입 등) 처리.
     * HTTP Status: 400 Bad Request
     * @param ex 발생한 예외
     * @return 에러 응답 DTO
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException occurred: {}", ex.getMessage());
        Map<String, Object> errorDetails = createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * RedisCommandExecutionException (예: WRONGTYPE) 및 그 상위 예외(Cause)를 처리합니다.
     * WebFlux 환경에서 RedisCommandExecutionException이 다른 런타임 예외로 래핑되어 전달될 수 있으므로,
     * getCause()를 통해 원본 예외를 확인합니다.
     * HTTP Status: 400 Bad Request (클라이언트의 잘못된 Redis 작업 시도이므로)
     * @param ex 발생한 예외
     * @return 에러 응답 DTO
     */
    @ExceptionHandler(Exception.class) // 모든 예외를 잡아서 RedisCommandExecutionException 여부 확인
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Throwable cause = ex.getCause();

        if (ex instanceof RedisCommandExecutionException || (cause != null && cause instanceof RedisCommandExecutionException)) {
            RedisCommandExecutionException redisEx = (RedisCommandExecutionException) (ex instanceof RedisCommandExecutionException ? ex : cause);
            log.error("RedisCommandExecutionException occurred: {}", redisEx.getMessage(), redisEx);

            String errorMessage = redisEx.getMessage();
            if (errorMessage != null && errorMessage.contains("WRONGTYPE")) {
                errorMessage = "Redis operation failed: " + errorMessage + ". Please check if the key's data type matches the operation type.";
            } else {
                errorMessage = "Redis operation failed: " + errorMessage;
            }

            Map<String, Object> errorDetails = createErrorResponse(
                    HttpStatus.BAD_REQUEST, // 클라이언트 요청이 잘못된 경우로 간주
                    "Redis Command Execution Error",
                    errorMessage
            );
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        // 그 외의 처리되지 않은 모든 예외 (Generic Exception) 처리.
        // HTTP Status: 500 Internal Server Error
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        Map<String, Object> errorDetails = createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred. Please try again later."
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // 에러 응답 맵을 생성하는 헬퍼 메서드
    private Map<String, Object> createErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", error);
        errorDetails.put("message", message);
        return errorDetails;
    }
}