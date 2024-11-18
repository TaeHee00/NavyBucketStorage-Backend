package com.kancth.navybucketstorage.global.exception;

import com.kancth.navybucketstorage.global.exception.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Message> globalExceptionHandler(BaseException e) {
        log.error("On Exception: {}, HttpStatus: {}", e.getMessage(), e.getHttpStatus());
        return ResponseEntity.status(e.getHttpStatus())
                             .body(Message.of(e.getMessage()));
    }
}
