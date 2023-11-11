package br.com.dv.account.exception;

import br.com.dv.account.exception.custom.*;
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
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                          WebRequest request) {
        String fieldErrorsMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                fieldErrorsMessage,
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InvalidEmailDomainException.class,
            UserEmailAlreadyExistsException.class,
            PasswordLengthException.class,
            BreachedPasswordException.class,
            SamePasswordException.class
    })
    public ResponseEntity<CustomErrorResponse> handleUserValidationExceptions(Exception ex, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthenticationMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleUserAuthenticationMismatchException(
            UserAuthenticationMismatchException ex,
            WebRequest request
    ) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record CustomErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
    }

}
