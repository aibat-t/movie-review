package kz.aibat.moviereview.repository;

import kz.aibat.moviereview.model.MoviePoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface MoviePosterRepository extends JpaRepository<MoviePoster, Long> {
    Optional<MoviePoster> findByName(String name);
}
