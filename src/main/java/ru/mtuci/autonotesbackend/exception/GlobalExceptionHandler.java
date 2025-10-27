package ru.mtuci.autonotesbackend.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mtuci.autonotesbackend.exception.dto.ErrorResponseDto;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.warn("Conflict: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Not Found: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("Validation error: {}", errorMessage);
        return createErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access Denied: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDto> handleJwtException(JwtException ex) {
        log.warn("JWT processing error: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.CONFLICT, "A user with the given username or email already exists.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtException(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please contact support.");
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(HttpStatus status, String message) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(status, message);
        return new ResponseEntity<>(errorResponse, status);
    }
}