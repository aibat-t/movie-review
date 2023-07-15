package kz.aibat.moviereview.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kz.aibat.moviereview.dto.MoviePosterDTO;
import kz.aibat.moviereview.model.Movie;
import kz.aibat.moviereview.model.MoviePoster;
import kz.aibat.moviereview.repository.MoviePosterRepository;
import kz.aibat.moviereview.repository.MovieRepository;
import kz.aibat.moviereview.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviePosterService {

    private final MoviePosterRepository moviePosterRepository;
    private final MovieRepository movieRepository;

    public MoviePosterDTO uploadImage(MultipartFile file, Long id) throws IOException {

        Optional<Movie> movieOptional = movieRepository.findById(id);
        if(movieOptional.isEmpty()){
            throw new EntityNotFoundException("no movie with this id");
        }

        Movie movie = movieOptional.get();

        MoviePoster moviePoster = moviePosterRepository.save(MoviePoster.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .build());

        movie.setPoster(moviePoster);

        movieRepository.save(movie);

        return MoviePosterDTO.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .build();
    }

    @Transactional
    public byte[] getImage(String name) throws FileNotFoundException {
        Optional<MoviePoster> dbPoster = moviePosterRepository.findByName(name);
        if(dbPoster.isEmpty()){
            throw new FileNotFoundException("post name not found");
        }

        return ImageUtil.decompressImage(dbPoster.get().getImageData());
    }
}
