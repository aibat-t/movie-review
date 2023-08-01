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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Value("${application.paging.reviewSize}")
    private Integer pageSize;

    public Page<ReviewDTO> getAllByPageAndMovieId(Integer pageNo, Long movieId) {
        Integer actualPageSize = pageSize;

        User currentUser = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Optional<Review> currentUserReview = reviewRepository
                .findReviewByUserIdAndMovieId(currentUser.getId(), movieId);
        if(currentUserReview.isPresent()){
            actualPageSize = pageSize - 1;

            //TODO создать свой pageble (size, total elements, content (где первый элемент текущего пользователя) и тд)
            //туда все записать
        }

        Pageable pageable = PageRequest.of(pageNo, actualPageSize);
        Page<Review> reviewPage = reviewRepository.findReviewByMovieIdOrderByPublicDateDesc(movieId, pageable);

        return reviewPage.map(this::createReviewDTO);
    }

    public ReviewDTO getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("no review with this id")
                );

        return createReviewDTO(review);
    }

    //TODO нужно что бы записывалось по id movie и использовать в DTO id юзера и фильма
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

    //TODO убрать из DTO пользователя и добаить туда его id
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
