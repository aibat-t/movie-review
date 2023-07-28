package kz.aibat.moviereview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date releaseDate;
    private String director;
    private String synopsis;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MoviePoster poster;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Review> reviewList;
}
