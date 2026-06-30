package com.jobx.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jobx.server.web.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> notFound(

            ResourceNotFoundException ex){

        log.error(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)

                .body(ApiResponse.error(ex.getMessage()));

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> badRequest(

            BadRequestException ex){

        log.warn(ex.getMessage());

        return ResponseEntity.badRequest()

                .body(ApiResponse.error(ex.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> validation(

            MethodArgumentNotValidException ex){

        String message=

                ex.getBindingResult()

                .getFieldError()

                .getDefaultMessage();

        return ResponseEntity.badRequest()

                .body(ApiResponse.error(message));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> exception(

            Exception ex){

        log.error("Unhandled Exception",ex);

        return ResponseEntity.status(500)

                .body(ApiResponse.error(
                        "Internal Server Error"));

    }

}