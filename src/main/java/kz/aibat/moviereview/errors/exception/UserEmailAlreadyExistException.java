package kz.aibat.moviereview.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailAlreadyExistException extends RuntimeException{
    public UserEmailAlreadyExistException(String msg) {
        super(msg);
    }
}
