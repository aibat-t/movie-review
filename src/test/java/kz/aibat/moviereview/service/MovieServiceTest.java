package kz.aibat.moviereview.service;


import jakarta.persistence.EntityNotFoundException;
import kz.aibat.moviereview.dto.MovieDTO;
import kz.aibat.moviereview.model.Movie;
import kz.aibat.moviereview.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        this.movieService = new MovieService(movieRepository);
    }

    @Test
    void itShouldGetEmptyListOfMoviePage() {
        Pageable pageable = PageRequest.of(0, 12);
        when(movieRepository.findAllByOrderByReleaseDateDesc(pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));
        var movieFromDb = movieService.getAllByPage(0);
        assertThat(movieFromDb).isEmpty();
    }

    //two types of mock data when ... whenReturn and given ... willReturn
    @Test
    void itShouldGetOneMovieInPage() {
        Pageable pageable = PageRequest.of(0, 12);
        Movie movie1 = new Movie(1L, "Film1", new Date(), "Director1", "Sinopsys", null, Collections.emptyList());
        //when(movieRepository.findAllByOrderByReleaseDateDesc(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(movie1), pageable, 0));
        given(movieRepository.findAllByOrderByReleaseDateDesc(any())).willReturn(new PageImpl<>(Collections.singletonList(movie1), pageable, 0));
        var movieFromDb = movieService.getAllByPage(0);
        assertThat(movieFromDb.getTotalElements() == 1).isTrue();
        assertThat(movieFromDb.getContent().get(0).getName()).isEqualTo("Film1");
    }

    @Test
    void willThrownExceptionIfNotExist() {
        Long id = 2L;

        assertThatThrownBy(() -> movieService.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("no movie with this id +" + id);
    }

    @Test
    void itShouldFindMovieById() {
        //given
        Long id = 2L;
        Movie movie2 = new Movie(id, "Film2", new Date(), "Director2", "Sinopsys2", null, Collections.emptyList());
        //when
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie2));
        MovieDTO actualMovieDTO = movieService.getById(id);
        //then
        assertThat(actualMovieDTO.getName()).isEqualTo(movie2.getName());
        assertThat(actualMovieDTO.getReleaseDate()).isEqualTo(movie2.getReleaseDate());
        assertThat(actualMovieDTO.getDirector()).isEqualTo(movie2.getDirector());
        assertThat(actualMovieDTO.getSynopsis()).isEqualTo(movie2.getSynopsis());
    }

    @Test
    void canAddMovie() {
        //given
        MovieDTO movieDTO1 = new MovieDTO(1L, "Film1", new Date(), "Director1", "Sinopsys", null);
        //then
        Movie movie1 = new Movie(1L, "Film1", new Date(), "Director1", "Sinopsys", null, Collections.emptyList());
        given(movieRepository.save(any(Movie.class))).willReturn(movie1);
        MovieDTO actualMovieDTO = movieService.add(movieDTO1);
        //when
        verify(movieRepository).save(any(Movie.class));
        assertThat(actualMovieDTO.getName()).isEqualTo(movieDTO1.getName());
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    @Disabled
    void update() {
    }
}