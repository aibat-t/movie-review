package kz.aibat.moviereview.auth;

import jakarta.validation.Valid;
import kz.aibat.moviereview.errors.exception.PasswordNotEqualToPasswordConfirmException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody @Valid RegisterRequest request
    ){
        if(!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new PasswordNotEqualToPasswordConfirmException("password not equal to password confirm");
        }

        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody @Valid AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
