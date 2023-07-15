package kz.aibat.moviereview.controller;

import jakarta.persistence.EntityNotFoundException;
import kz.aibat.moviereview.dto.MovieDTO;
import kz.aibat.moviereview.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    public final MovieService movieService;

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<MovieDTO>> getMoviePage (@PathVariable("page") Integer page) {
        return ResponseEntity.ok(movieService.getAllByPage(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById (@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(movieService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<MovieDTO> addMovie (@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.add(movieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteMovie(@PathVariable("id") Long id) {
        return ResponseEntity.ok(movieService.delete(id));
    }
}
