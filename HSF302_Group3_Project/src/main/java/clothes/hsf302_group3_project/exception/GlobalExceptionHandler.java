package clothes.hsf302_group3_project.exception;

import clothes.hsf302_group3_project.dto.response.ErrorResponse;
import clothes.hsf302_group3_project.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.RESOURCE_NOT_FOUND.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.RESOURCE_NOT_FOUND.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        List<String> messages = fieldErrors.values().stream().toList();
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_FORMAT.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        ErrorResponse error = new ErrorResponse(ErrorCode.CONSTRAINT_VIOLATION.getCode(), errors, request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgument(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorResponse error = new ErrorResponse(ErrorCode.CONSTRAINT_VIOLATION.getCode(), errors, request.getRequestURI());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExistException(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.RESOURCE_ALREADY_EXISTS.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.RESOURCE_ALREADY_EXISTS.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.UNAUTHORIZED.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.UNAUTHORIZED.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.RESOURCE_NOT_FOUND.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.RESOURCE_NOT_FOUND.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.ACTION_NOT_PERMITTED.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.ACTION_NOT_PERMITTED.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.ACTION_NOT_PERMITTED.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.ACTION_NOT_PERMITTED.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> handleJsonProcessingException(JsonProcessingException ex, HttpServletRequest request) {
        List<String> messages = List.of(
                ex.getMessage() != null ? ex.getMessage() : ErrorCode.INVALID_FORMAT.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_FORMAT.getCode(),
                messages,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex, HttpServletRequest request) {
        String message = ex.getMessage() != null
                ? ex.getMessage()
                : ErrorCode.FILE_IO_ERROR.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.FILE_IO_ERROR.getCode(),
                List.of(message),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
