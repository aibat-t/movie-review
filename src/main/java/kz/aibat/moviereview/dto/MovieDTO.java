package kz.aibat.moviereview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MovieDTO {

    private Long id;

    @Size(min=2,max=100)
    private String name;

    @JsonProperty("release_date")
    private Date releaseDate;

    @Size(min=2,max=100)
    private String director;

    @Size(min=2,max=1000)
    private String synopsis;

    @JsonProperty("poster_name")
    private String posterName;
}
