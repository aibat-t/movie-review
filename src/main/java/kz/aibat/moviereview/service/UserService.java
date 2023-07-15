package kz.aibat.moviereview.service;

import jakarta.persistence.EntityNotFoundException;
import kz.aibat.moviereview.model.User;
import kz.aibat.moviereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    protected User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                    () -> new EntityNotFoundException("no user with this id")
            );
    }
}
