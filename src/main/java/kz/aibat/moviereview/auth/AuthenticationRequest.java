package kz.aibat.moviereview.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {


    @JsonProperty("email")
    @Email(message = "Invalid email format")
    @Size(min = 2, max = 100, message = "Email length should be between 2 and 100")
    private String email;

    @JsonProperty("password")
    @Size(min = 8, max = 50, message = "Password length should be between 2 and 100")
    private String password;
}
