package br.com.dv.account.exception;

import br.com.dv.account.exception.custom.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponseEntity(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request
    ) {
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return buildResponseEntity(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(UserAuthenticationMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleUserAuthenticationMismatchException(
            UserAuthenticationMismatchException ex,
            WebRequest request
    ) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler({
            InvalidEmailDomainException.class,
            UserEmailAlreadyExistsException.class,
            PasswordLengthException.class,
            BreachedPasswordException.class,
            SamePasswordException.class,
            EmployeeNotFoundException.class,
            PaymentNotFoundException.class,
            InvalidPeriodException.class,
            NegativeSalaryException.class,
            NonUniqueEmployeePeriodPairException.class,
            AdminRestrictionException.class,
            RoleNotAssignedException.class,
            LastRoleException.class,
            RoleConflictException.class,
            CannotLockAdminException.class
    })
    public ResponseEntity<CustomErrorResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<CustomErrorResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    private ResponseEntity<CustomErrorResponse> buildResponseEntity(HttpStatus status, String msg, WebRequest req) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                msg,
                extractRequestUri(req)
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    private String extractRequestUri(WebRequest req) {
        return ((ServletWebRequest) req).getRequest().getRequestURI();
    }

    public record CustomErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
    }

}
