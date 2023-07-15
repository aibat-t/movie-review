package kz.aibat.moviereview.repository;

import kz.aibat.moviereview.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Transactional
    Integer deleteReviewById(Long id);

    Page<Review> findAllByOrderByPublicDateDesc(Pageable page);
}
