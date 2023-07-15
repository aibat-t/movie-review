package kz.aibat.moviereview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import kz.aibat.moviereview.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("role_list")
    private List<Role> roleList;
}
