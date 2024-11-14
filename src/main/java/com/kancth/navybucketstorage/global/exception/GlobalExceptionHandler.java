package com.kancth.navybucketstorage.global.exception;

import lombok.Builder;
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

@Builder
record Message(
        String message
) {
    public static Message of(String message) {
        return Message.builder()
                    .message(message)
                    .build();
    }
}
