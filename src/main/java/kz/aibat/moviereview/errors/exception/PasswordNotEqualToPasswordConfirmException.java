package kz.aibat.moviereview.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PasswordNotEqualToPasswordConfirmException extends RuntimeException{
    public PasswordNotEqualToPasswordConfirmException(String msg) {
        super(msg);
    }
}
