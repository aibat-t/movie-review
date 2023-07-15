package kz.aibat.moviereview.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import kz.aibat.moviereview.errors.exception.JwtTokenExpiredException;
import kz.aibat.moviereview.errors.exception.PasswordNotEqualToPasswordConfirmException;
import kz.aibat.moviereview.errors.exception.UserEmailAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        ApiError error = new ApiError(400, ex.getMessage(), request.getServletPath());

        Map<String, String> argsErrorList = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();

            argsErrorList.put(fieldName, errorMessage);
        });

        error.setValidationErrors(argsErrorList);

        return error;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({PasswordNotEqualToPasswordConfirmException.class, UserEmailAlreadyExistException.class})
    public ApiError handlePasswordException(
            RuntimeException ex,
            HttpServletRequest request) {

        return new ApiError(409, ex.getMessage(), request.getServletPath());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiError handleAuthenticationException(
            BadCredentialsException ex,
            HttpServletRequest request) {

        return new ApiError(401, ex.getMessage(), request.getServletPath());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        return new ApiError(204, ex.getMessage(), request.getServletPath());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public ApiError handleFileNotFoundException(
            FileNotFoundException ex,
            HttpServletRequest request
    ) {
        return new ApiError(404, ex.getMessage(), request.getServletPath());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ApiError handleJwtTokenExpiredException(
            JwtTokenExpiredException ex,
            HttpServletRequest request
    ) {
        return new ApiError(401, ex.getMessage(), request.getServletPath());
    }
}
