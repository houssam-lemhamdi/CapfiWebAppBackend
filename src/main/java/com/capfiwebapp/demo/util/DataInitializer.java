package com.capfiwebapp.demo.util;

import com.capfiwebapp.demo.data.model.Movie;
import com.capfiwebapp.demo.data.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;

    private static long counter = 0;

    public DataInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (movieRepository.count() == 0) {
            initDatabase();
        }
    }

    private void initDatabase() {
        try (InputStream is = getClass().getResourceAsStream("/data.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            reader.lines().skip(1)
                    .map(line -> line.split(","))
                    .map(fields -> {
                        Movie movie = new Movie();
                        movie.setTconst(fields[0]);
                        movie.setTitleType(fields[1]);
                        movie.setPrimaryTitle(fields[2]);
                        movie.setOriginalTitle(fields[3]);
                        movie.setIsAdult(fields[4]);
                        try{
                            movie.setStartYear(Year.parse(fields[5]));
                        }catch (Exception e){
                            movie.setStartYear(null);
                            log.debug("year incorrect for element with id {} value {}", fields[0], fields[5]);
                        }
                        try{
                            movie.setEndYear(Year.parse(fields[6]));
                        }catch (Exception e){
                            movie.setEndYear(null);
                            log.debug("year incorrect for element with id {} value {}", fields[0], fields[6]);
                        }
                        try{
                            movie.setRuntimeMinutes(Long.parseLong(fields[7]));
                        }catch (Exception e){
                            movie.setRuntimeMinutes(null);
                            log.debug("run time incorrect for element with id {} value {}", fields[0], fields[7]);
                        }
                        movie.setGenres(fields[8]);
                        return movie;
                    })
                    .forEach(movie ->{
                        movieRepository.save(movie);
                        if((++counter % 1000) == 0 ){
                            log.info("inserting is in progress... inserted rows {}", counter);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
