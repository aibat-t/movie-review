package kz.aibat.moviereview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;

    @Size(min = 4, max = 150)
    private String title;

    @Size(min = 40, max = 1500)
    private String mainText;

    @NotBlank
    private Date publicDate;

    @NotBlank
    private UserDTO author;
}
