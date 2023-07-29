package kz.aibat.moviereview.repository;

import kz.aibat.moviereview.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findAllByOrderByReleaseDateDesc(Pageable page);

    Integer deleteMovieById(Long id);
}
