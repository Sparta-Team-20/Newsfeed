package com.example.newsfeed.common.config;


import com.example.newsfeed.common.exception.CustomExceptionHandler;
import com.example.newsfeed.common.exception.ErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({CustomExceptionHandler.class})
    private ResponseEntity handleCustomException(CustomExceptionHandler ex) {
        return getErrorResponse(ex.getErrorCode(), ex.getErrorCode().getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    private ResponseEntity handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElseThrow(() -> new IllegalArgumentException("검증 에러가 존재해야 합니다."));

        return getErrorResponse(ErrorCode.DATA_BAD_REQUEST, errorMessage);
    }

    private ResponseEntity<Map<String, Object>> getErrorResponse(ErrorCode errorCode, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.valueOf(errorCode.getStatus()));
        errorResponse.put("code", errorCode.getStatus());
        errorResponse.put("message", message);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }
}