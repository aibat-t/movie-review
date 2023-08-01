package kz.aibat.moviereview.repository;

import kz.aibat.moviereview.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Transactional
    Integer deleteReviewById(Long id);

    @Query("SELECT r FROM Review r where r.movie.id = :movie_id ORDER BY r.publicDate DESC ")
    Page<Review> findReviewByMovieIdOrderByPublicDateDesc(
            @Param("movie_id") Long movieId,
            Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.author.id = :user_id and r.movie.id = :movie_id")
    Optional<Review> findReviewByUserIdAndMovieId(
            @Param("user_id") Long userId,
            @Param("movie_id") Long movieId
    );
}
