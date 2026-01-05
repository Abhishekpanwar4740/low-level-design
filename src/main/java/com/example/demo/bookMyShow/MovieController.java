package com.example.demo.bookMyShow;

import com.example.demo.bookMyShow.enums.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieController {
    Map<City, List<Movie>> cityMovieMap;
    List<Movie> allMovies;

     MovieController() {
        this.cityMovieMap = new HashMap<>();
        this.allMovies = new ArrayList<>();

    }
    //ADD movie to a particular city, make use of cityVsMovies map
    void addMovie(Movie movie, City city) {

        allMovies.add(movie);

        List<Movie> movies = cityMovieMap.getOrDefault(city, new ArrayList<>());
        movies.add(movie);
        cityMovieMap.put(city, movies);
    }


    Movie getMovieByName(String movieName) {

        for(Movie movie : allMovies) {
            if((movie.getName()).equals(movieName)) {
                return movie;
            }
        }
        return null;
    }


    List<Movie> getMoviesByCity(City city) {
        return cityMovieMap.get(city);
    }
    //REMOVE movie from a particular city, make use of cityVsMovies map

    //UPDATE movie of a particular city, make use of cityVsMovies map

    //CRUD operation based on Movie ID, make use of allMovies list

    //CRUD

}
