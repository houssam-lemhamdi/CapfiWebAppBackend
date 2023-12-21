package com.capfiwebapp.demo.data.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.time.Year;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(generator = "custom-id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "custom-id", strategy = "com.capfiwebapp.demo.conf.CustomIdGenerator")
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private String isAdult;
    private Year startYear;
    private Year endYear;
    private Long runtimeMinutes;
    private String genres;

}



