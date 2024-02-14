package com.nand.assignment.movierental.repository.impl;

import com.nand.assignment.movierental.repository.IMovieRepository;
import com.nand.assignment.movierental.model.Movie;
import com.nand.assignment.movierental.utils.MovieCategory;

import java.util.HashMap;
import java.util.Optional;

/**
 * Repository class for getting movies, adding movie
 */
public class MovieRepository implements IMovieRepository {

    public HashMap<String, Optional<Movie>> getMovies() {

        var movies = new HashMap<String, Optional<Movie>>();
        movies.put("F001", Optional.of(new Movie("You've Got Mail", MovieCategory.REGULAR.getCode())));
        movies.put("F002", Optional.of(new Movie("Matrix",  MovieCategory.REGULAR.getCode())));
        movies.put("F003", Optional.of(new Movie("Cars",  MovieCategory.CHILDREN.getCode())));
        movies.put("F004", Optional.of(new Movie("Fast & Furious X",  MovieCategory.NEW.getCode())));

        return movies;
    }

}
