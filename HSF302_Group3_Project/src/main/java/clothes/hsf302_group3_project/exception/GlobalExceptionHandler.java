package clothes.hsf302_group3_project.exception;

import clothes.hsf302_group3_project.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException ex, HttpServletRequest request) {
        return createErrorPage(ex.getMessage(), request);
    }

    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    public ModelAndView handleNotFound(Exception ex, HttpServletRequest request) {
        return createErrorPage(ex.getMessage(), request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ModelAndView handleExists(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        return createErrorPage(ex.getMessage(), request);
    }

    @ExceptionHandler({UnauthorizedException.class, AccessDeniedException.class})
    public ModelAndView handleUnauthorized(Exception ex, HttpServletRequest request) {
        return createErrorPage(ex.getMessage(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();
        return createErrorPage(String.join("<br>", errors), request);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ModelAndView handleValidationExceptions(Exception ex, HttpServletRequest request) {
        List<String> messages;

        if (ex instanceof MethodArgumentNotValidException manvEx) {
            messages = manvEx.getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
        } else if (ex instanceof BindException bindEx) {
            messages = bindEx.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
        } else {
            messages = List.of("Lỗi không xác định");
        }

        return createErrorPage(String.join("<br>", messages), request);
    }

    @ExceptionHandler({IOException.class})
    public ModelAndView handleIO(IOException ex, HttpServletRequest request) {
        return createErrorPage(ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknown(Exception ex, HttpServletRequest request) {
        return createErrorPage("Lỗi hệ thống: " + ex.getMessage(), request);
    }

    private ModelAndView createErrorPage(String message, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error"); // Trang error.jsp hoặc error.html
        mav.addObject("errorMessage", message);
        mav.addObject("path", request.getRequestURI());
        return mav;
    }
}
