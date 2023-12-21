package com.capfiwebapp.demo.controller;

import com.capfiwebapp.demo.data.model.Movie;
import com.capfiwebapp.demo.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin("*")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping
    public ResponseEntity<Page<Movie>> getAllEntities(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(movieService.getAllEntities(pageable));
    }

    @PostMapping
    public ResponseEntity<Movie> addEntity(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Movie>> searchEntities(
            @RequestParam(required = false) String primaryTitle,
            @RequestParam(required = false) String originalTitle,
            @RequestParam(required = false) String startYear,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(movieService.searchEntities(primaryTitle, originalTitle, startYear, pageable));
    }

    @GetMapping("/sort")
    public ResponseEntity<Page<Movie>> getEntities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startYear") String sortBy,
            @RequestParam(defaultValue = "DESC") String order
    ) {
        Page<Movie> sortedEntities = movieService.getAllEntitiesSorted(sortBy, page, size, order);
        return ResponseEntity.ok(sortedEntities);
    }


    @GetMapping("/filter")
    public ResponseEntity<Page<Movie>> getFilteredEntitiesByStartYear(
            @RequestParam(defaultValue = "1888") String startYear,
            @RequestParam(defaultValue = "AFTER") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Movie> filteredMovies = movieService.getAllEntitiesFilteredByStartYear(startYear, order, page, size);
        return ResponseEntity.ok(filteredMovies);
    }
}
