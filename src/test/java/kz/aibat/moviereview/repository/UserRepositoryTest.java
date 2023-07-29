package kz.aibat.moviereview.repository;

import kz.aibat.moviereview.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        //given
        String email = "tozhybaev@mail.com";
        User user = new User();
        user.setFirstName("Aibat");
        user.setLastName("Tozhybayev");
        user.setEmail(email);
        user.setPassword("123456789");
        user.setRoleList(Collections.emptyList());
        userRepository.save(user);
        //when
        boolean exists = userRepository.findByEmail(email).isPresent();
        //then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldntFindUserByEmailDoesNotExist() {
        //given
        String email = "tozhybaev@mail.com";
        //when
        System.out.println(userRepository.findByEmail(email).isPresent());
        boolean exists = userRepository.findByEmail(email).isPresent();
        //then
        assertThat(exists).isFalse();
    }
}
