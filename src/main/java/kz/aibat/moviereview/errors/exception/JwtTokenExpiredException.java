package kz.aibat.moviereview.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String msg) {
        super(msg);
    }
}
