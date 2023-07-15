package kz.aibat.moviereview.controller;

import kz.aibat.moviereview.dto.UserDTO;
import kz.aibat.moviereview.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping()
    public ResponseEntity<UserDTO> getUserDetails() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = UserDTO.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .email(currentUser.getEmail())
                .roleList(currentUser.getRoleList())
                .build();
        return ResponseEntity.ok(userDTO);
    }
}
