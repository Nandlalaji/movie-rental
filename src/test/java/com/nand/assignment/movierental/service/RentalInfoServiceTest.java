package com.nand.assignment.movierental.service;

import com.nand.assignment.movierental.exception.MovieNotFoundException;
import com.nand.assignment.movierental.exception.UnKnownCategoryException;
import com.nand.assignment.movierental.model.Customer;
import com.nand.assignment.movierental.model.Movie;
import com.nand.assignment.movierental.model.MovieRental;
import com.nand.assignment.movierental.repository.impl.MovieRepository;
import com.nand.assignment.movierental.service.impl.ChildrenMovieRental;
import com.nand.assignment.movierental.service.impl.NewMovieRental;
import com.nand.assignment.movierental.service.impl.RegularMovieRental;
import com.nand.assignment.movierental.service.impl.RentalInfoService;
import com.nand.assignment.movierental.utils.MovieCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.nand.assignment.movierental.utils.Constants.INVALID_MOVIE_CATEGORY_ERROR_MESSAGE;
import static com.nand.assignment.movierental.utils.Constants.INVALID_MOVIE_CODE_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class RentalInfoServiceTest {
    final MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

    final RentalFactory rentalFactory = Mockito.mock(RentalFactory.class);

    private final IRentalInfoService testRentalInfoService = new RentalInfoService(movieRepository, rentalFactory);

    @Test
    @DisplayName("movie with regular movies only")
    void testStatementSuccess() {

        String expected =
                """
                                      Rental Record for C. U. Stomer
                                      \tYou've Got Mail\t3.5
                                      \tMatrix\t2.0
                                      Amount owed is 5.5
                                      You earned 2 frequent points
                                      """;

        var movies = new HashMap<String, Optional<Movie>>();
        movies.put("F001", Optional.of(new Movie("You've Got Mail", MovieCategory.REGULAR.getCode())));
        movies.put("F002", Optional.of(new Movie("Matrix",  MovieCategory.REGULAR.getCode())));
        when(movieRepository.getMovies()).thenReturn(movies);
        when(rentalFactory.getRentalObjectByCategory(MovieCategory.REGULAR.getCode())).thenReturn(new RegularMovieRental());

        List<MovieRental> movieRentals =
                List.of(new MovieRental("F001", 3), new MovieRental("F002", 1));
        var customer = new Customer("C. U. Stomer", movieRentals);
        String actual = testRentalInfoService.statement(customer);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test with not provided movie code")
    void testStatementMovieNotExist() {

        var movies = new HashMap<String, Optional<Movie>>();
        movies.put("F001", Optional.of(new Movie("You've Got Mail", MovieCategory.REGULAR.getCode())));
        movies.put("F002", Optional.of(new Movie("Matrix",  MovieCategory.REGULAR.getCode())));
        when(movieRepository.getMovies()).thenReturn(movies);
        var thrown =
                assertThrows(
                        MovieNotFoundException.class,
                        () -> {
                            List<MovieRental> movieRentals =
                                    List.of(new MovieRental("F010", 3), new MovieRental("F002", 1));
                            var customer = new Customer("C. U. Stomer", movieRentals);
                            String actual = testRentalInfoService.statement(customer);
                        });
        assertEquals("F010" + INVALID_MOVIE_CODE_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    @DisplayName("Test with not provided movie code")
    void testStatementCategoryNotExist() {

        var movies = new HashMap<String, Optional<Movie>>();
        movies.put("F001", Optional.of(new Movie("You've Got Mail", MovieCategory.REGULAR.getCode())));
        movies.put("F002", Optional.of(new Movie("Matrix",  MovieCategory.REGULAR.getCode())));
        movies.put("F003", Optional.of(new Movie("Matrix",  "Unknown")));
        when(movieRepository.getMovies()).thenReturn(movies);
        when(rentalFactory.getRentalObjectByCategory(MovieCategory.REGULAR.getCode())).thenThrow(new UnKnownCategoryException("Unknown" + INVALID_MOVIE_CATEGORY_ERROR_MESSAGE));
        var thrown =
                assertThrows(
                        UnKnownCategoryException.class,
                        () -> {
                            List<MovieRental> movieRentals =
                                    List.of(new MovieRental("F001", 3), new MovieRental("F002", 1), new MovieRental("F003", 3));
                            var customer = new Customer("C. U. Stomer", movieRentals);
                            String actual = testRentalInfoService.statement(customer);
                        });
        assertEquals("Unknown" + INVALID_MOVIE_CATEGORY_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    @DisplayName("Test with new movie in rental list")
    void testStatementWithNewMovie() {

        String expected =
                """
                                          Rental Record for C. U. Stomer
                                          \tYou've Got Mail\t3.5
                                          \tMatrix\t2.0
                                          \tFast & Furious X\t9.0
                                          Amount owed is 14.5
                                          You earned 4 frequent points
                                          """;

        var movies = new HashMap<String, Optional<Movie>>();
        movies.put("F001", Optional.of(new Movie("You've Got Mail", MovieCategory.REGULAR.getCode())));
        movies.put("F002", Optional.of(new Movie("Matrix",  MovieCategory.REGULAR.getCode())));
        movies.put("F004", Optional.of(new Movie("Fast & Furious X", MovieCategory.NEW.getCode())));
        when(movieRepository.getMovies()).thenReturn(movies);

        when(rentalFactory.getRentalObjectByCategory(MovieCategory.REGULAR.getCode())).thenReturn(new RegularMovieRental());
        when(rentalFactory.getRentalObjectByCategory(MovieCategory.NEW.getCode())).thenReturn(new NewMovieRental());

        List<MovieRental> movieRentals =
                List.of(new MovieRental("F001", 3), new MovieRental("F002", 1), new MovieRental("F004", 3));
        var customer = new Customer("C. U. Stomer", movieRentals);
        String actual = testRentalInfoService.statement(customer);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test with all types movie in rental list")
    void testStatementWithAllTypeMovies() {

        String expected =
                """
                                          Rental Record for C. U. Stomer
                                          \tYou've Got Mail\t3.5
                                          \tMatrix\t2.0
                                          \tCars\t1.5
                                          \tFast & Furious X\t9.0
                                          Amount owed is 16.0
                                          You earned 5 frequent points
                                          """;

        var movies = new HashMap<String, Optional<Movie>>();
        movies.put("F001", Optional.of(new Movie("You've Got Mail", MovieCategory.REGULAR.getCode())));
        movies.put("F002", Optional.of(new Movie("Matrix",  MovieCategory.REGULAR.getCode())));
        movies.put("F003", Optional.of(new Movie("Cars", MovieCategory.CHILDREN.getCode())));
        movies.put("F004", Optional.of(new Movie("Fast & Furious X", MovieCategory.NEW.getCode())));
        when(movieRepository.getMovies()).thenReturn(movies);

        when(rentalFactory.getRentalObjectByCategory(MovieCategory.REGULAR.getCode())).thenReturn(new RegularMovieRental());
        when(rentalFactory.getRentalObjectByCategory(MovieCategory.CHILDREN.getCode())).thenReturn(new ChildrenMovieRental());
        when(rentalFactory.getRentalObjectByCategory(MovieCategory.NEW.getCode())).thenReturn(new NewMovieRental());

        List<MovieRental> movieRentals =
                List.of(new MovieRental("F001", 3), new MovieRental("F002", 1),
                        new MovieRental("F003", 3),new MovieRental("F004", 3));
        var customer = new Customer("C. U. Stomer", movieRentals);
        String actual = testRentalInfoService.statement(customer);
        assertEquals(expected, actual);
    }
}
