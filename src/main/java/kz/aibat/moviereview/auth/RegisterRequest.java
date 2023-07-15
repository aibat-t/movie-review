package kz.aibat.moviereview.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @JsonProperty("first_name")
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    @JsonProperty("email")
    @Email(message = "Invalid email format")
    @Size(min = 2, max = 100, message = "Email length should be between 2 and 100")
    private String email;

    @JsonProperty("password")
    @Size(min = 8, max = 50, message = "Password length should be between 2 and 100")
    private String password;

    @JsonProperty("password_confirm")
    @Size(min = 8, max = 50, message = "Password confirm length should be between 2 and 100")
    private String passwordConfirm;
}
