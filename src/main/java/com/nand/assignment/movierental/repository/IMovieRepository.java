package com.nand.assignment.movierental.repository;

import com.nand.assignment.movierental.model.Movie;

import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for getting movies, adding movie
 */
public interface IMovieRepository {

    Map<String, Optional<Movie>> getMovies();

}
