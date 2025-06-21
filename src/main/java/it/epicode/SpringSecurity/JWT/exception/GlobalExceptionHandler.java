package it.epicode.SpringSecurity.JWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ElementoNonTrovatoException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ElementoNonTrovatoException ex) {
        return createResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostiEsauritiException.class)
    public ResponseEntity<Map<String, Object>> handlePostiEsauriti(PostiEsauritiException ex) {
        return createResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PrenotazioneDuplicataException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicata(PrenotazioneDuplicataException ex) {
        return createResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<Map<String, Object>> createResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", message);
        error.put("status", status.value());
        return new ResponseEntity<>(error, status);
    }
}
