package kz.aibat.moviereview.controller;


import kz.aibat.moviereview.dto.ReviewDTO;
import kz.aibat.moviereview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/page")
    public ResponseEntity<Page<ReviewDTO>> getReviewPage (
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "movieId") Integer movieId) {
        return ResponseEntity.ok(reviewService.getAllByPage(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById (@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<ReviewDTO> addReview (@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.addReview(reviewDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteReview(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }

}
