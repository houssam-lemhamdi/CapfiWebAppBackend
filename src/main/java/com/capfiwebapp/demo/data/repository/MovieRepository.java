package com.capfiwebapp.demo.data.repository;

import com.capfiwebapp.demo.data.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Year;

public interface MovieRepository extends JpaRepository<Movie, Long>, QueryDslRepository<Movie> {

    Page<Movie> findByStartYearAfter(Year startYear, Pageable pageable);

    Page<Movie> findByStartYearBefore(Year startYear, Pageable pageable);
}
