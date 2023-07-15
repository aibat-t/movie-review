package kz.aibat.moviereview.auth;

import kz.aibat.moviereview.config.JwtService;
import kz.aibat.moviereview.errors.exception.UserEmailAlreadyExistException;
import kz.aibat.moviereview.model.Role;
import kz.aibat.moviereview.model.User;
import kz.aibat.moviereview.repository.RoleRepository;
import kz.aibat.moviereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UserEmailAlreadyExistException {
        Role userRole = roleRepository.findByAccessValue("ROLE_USER");

        Optional<User> checkUser = userRepository.findByEmail(request.getEmail());

        if(checkUser.isPresent()){
            throw new UserEmailAlreadyExistException("user with this email is already exist");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleList(Collections.singletonList(userRole))
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email does not exist"));
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }catch (BadCredentialsException ex){
            throw new BadCredentialsException(ex.getMessage());
        }
    }
}
