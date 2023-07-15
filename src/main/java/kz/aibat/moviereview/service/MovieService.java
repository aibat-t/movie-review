package kz.aibat.moviereview.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kz.aibat.moviereview.dto.MovieDTO;
import kz.aibat.moviereview.model.Movie;
import kz.aibat.moviereview.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Value("${application.paging.movieSize}")
    private Integer movieSize;

    @Transactional
    public Page<MovieDTO> getAllByPage(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, movieSize);

        Page<Movie> reviewPage = movieRepository.findAllByOrderByReleaseDateDesc(paging);

        return reviewPage.map(this::createMovieDTO);
    }

    @Transactional
    public MovieDTO getById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("no movie with this id")
                );
        return createMovieDTO(movie);
    }

    public MovieDTO add(MovieDTO movieDTO) {

        Movie movie = Movie.builder()
                .name(movieDTO.getName())
                .director(movieDTO.getDirector())
                .synopsis(movieDTO.getSynopsis())
                .releaseDate(movieDTO.getReleaseDate())
                .build();

        Movie newMovie = movieRepository.save(movie);

        return createMovieDTO(newMovie);
    }

    public Integer delete(Long id) {
        return movieRepository.deleteMovieById(id);
    }

    private MovieDTO createMovieDTO(Movie movie) {

        MovieDTO movieDTO =  MovieDTO.builder()
                .name(movie.getName())
                .id(movie.getId())
                .synopsis(movie.getSynopsis())
                .releaseDate(movie.getReleaseDate())
                .director(movie.getDirector())
                .build();

        if(movie.getPoster() != null){
            movieDTO.setPosterName(movie.getPoster().getName());
        }

        return movieDTO;
    }
}
