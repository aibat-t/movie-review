package kz.aibat.moviereview.service;


import jakarta.persistence.EntityNotFoundException;
import kz.aibat.moviereview.dto.ReviewDTO;
import kz.aibat.moviereview.dto.UserDTO;
import kz.aibat.moviereview.model.Review;
import kz.aibat.moviereview.model.User;
import kz.aibat.moviereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;


@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Value("${application.paging.reviewSize}")
    private Integer pageSize;

    public Page<ReviewDTO> getAllByPage(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Review> reviewPage =  reviewRepository.findAllByOrderByPublicDateDesc(paging);

        return reviewPage.map(this::createReviewDTO);
    }

    public ReviewDTO getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("no review with this id")
                );

        return createReviewDTO(review);
    }

    public ReviewDTO addReview(ReviewDTO reviewDTO){

        User user = userService.getUserById(reviewDTO.getAuthor().getId());

        Review review = Review.builder()
                .title(reviewDTO.getTitle())
                .author(user)
                .publicDate(new Date())
                .mainText(reviewDTO.getMainText())
                .build();

        Review newReview =  reviewRepository.save(review);

        return createReviewDTO(newReview);
    }

    public Integer deleteReview(Long id) {
        return reviewRepository.deleteReviewById(id);
    }

    private ReviewDTO createReviewDTO(Review review) {

        return ReviewDTO.builder()
                .id(review.getId())
                .title(review.getTitle())
                .mainText(review.getMainText())
                .publicDate(review.getPublicDate())
                .author(createUserDTO(review.getAuthor()))
                .build();
    }

    private UserDTO createUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
