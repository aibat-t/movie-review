package kz.aibat.moviereview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_poster")
public class MoviePoster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;
}
