package kz.aibat.moviereview.controller;

import kz.aibat.moviereview.dto.MoviePosterDTO;
import kz.aibat.moviereview.service.MoviePosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/poster")
@RequiredArgsConstructor
public class MoviePosterController {

    private final MoviePosterService moviePosterService;

    @PostMapping()
    public ResponseEntity<MoviePosterDTO> uploadPoster(@RequestParam("image") MultipartFile file, @RequestParam("id") Long id) throws IOException {
        return ResponseEntity.ok(moviePosterService.uploadImage(file, id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getPosterByName(@PathVariable("name") String name) throws FileNotFoundException {
        byte[] poster = moviePosterService.getImage(name);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(new InputStreamResource(new ByteArrayInputStream(poster)));
    }
}
