package ma.you.hospital.billing.web;

import ma.you.hospital.billing.services.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> business(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body("BUSINESS_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        Map<String, Object> b = body("VALIDATION_ERROR", "Invalid request");
        b.put("errors", errors);
        return ResponseEntity.badRequest().body(b);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body("INTERNAL_ERROR", "Unexpected error"));
    }

    private Map<String, Object> body(String code, String message) {
        Map<String, Object> b = new LinkedHashMap<>();
        b.put("timestamp", Instant.now().toString());
        b.put("code", code);
        b.put("message", message);
        return b;
    }
}
