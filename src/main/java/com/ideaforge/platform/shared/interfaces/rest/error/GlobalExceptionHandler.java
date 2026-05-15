package com.ideaforge.platform.shared.interfaces.rest.error;

import com.ideaforge.platform.shared.interfaces.rest.resources.MessageResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) { this.messageSource = messageSource; }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String globalMessage = messageSource.getMessage("error.validation", null, "Validation failed", locale);
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", globalMessage, "errors", fieldErrors));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<MessageResource> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(MessageResource.of(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageResource> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MessageResource.of("Data integrity violation. Review duplicated or related records."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResource> handleGenericException(Exception ex, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String msg = messageSource.getMessage("error.generic", null, "Unexpected error", locale);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResource.of(msg));
    }
}
