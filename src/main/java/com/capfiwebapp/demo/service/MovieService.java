package com.capfiwebapp.demo.service;

import com.capfiwebapp.demo.data.model.Movie;
import com.capfiwebapp.demo.data.model.QMovie;
import com.capfiwebapp.demo.data.repository.MovieRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    private final JPAQueryFactory queryFactory;

    public MovieService(MovieRepository movieRepository, JPAQueryFactory queryFactory) {
        this.movieRepository = movieRepository;
        this.queryFactory = queryFactory;
    }

    public void saveAll(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    public Page<Movie> getAllEntities(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> getAllEntitiesSorted(String sortBy, int page, int size, String order) {

        Sort sort = order.equals("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> searchEntities(String primaryTitle, String originalTitle, String startYear, Pageable pageable) {

        QMovie qMovie = QMovie.movie;

        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(primaryTitle)) {
            predicate = predicate.and(qMovie.primaryTitle.eq(primaryTitle));
        }

        if (Objects.nonNull(originalTitle)) {
            predicate = predicate.and(qMovie.originalTitle.eq(originalTitle));
        }

        if (Objects.nonNull(startYear)) {
            try {
                predicate = predicate.and(qMovie.startYear.eq(Year.parse(startYear)));
            } catch (Exception e) {
                log.error("date incorrect : value {}", startYear);
            }
        }

        List<Movie> resultList = queryFactory
                .selectFrom(qMovie)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(qMovie)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }


    public Page<Movie> getAllEntitiesFilteredByStartYear(String startYear, String order, int page, int size) {
        Year year = Year.parse(startYear);
        PageRequest pageable = PageRequest.of(page, size);
        if (order.equalsIgnoreCase("BEFORE"))
            return movieRepository.findByStartYearBefore(year, pageable);
        return movieRepository.findByStartYearAfter(year, pageable);
    }
}
