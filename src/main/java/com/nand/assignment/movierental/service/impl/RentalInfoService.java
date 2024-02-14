package com.nand.assignment.movierental.service.impl;

import com.nand.assignment.movierental.exception.MovieNotFoundException;
import com.nand.assignment.movierental.model.Customer;
import com.nand.assignment.movierental.model.MovieRental;
import com.nand.assignment.movierental.repository.IMovieRepository;
import com.nand.assignment.movierental.service.IRentalInfoService;
import com.nand.assignment.movierental.service.RentalFactory;
import com.nand.assignment.movierental.utils.Constants;
import com.nand.assignment.movierental.utils.MovieCategory;

import java.util.Optional;

import static com.nand.assignment.movierental.utils.Constants.*;

/**
 * A service class to generate amount statement for movie rental
 */
public class RentalInfoService implements IRentalInfoService {

    private final IMovieRepository movieRepository;
    private final RentalFactory rentalService;
    private double totalAmount = 0;
    private int frequentRenterPoints = 0;

    public RentalInfoService(IMovieRepository movieRepository, RentalFactory rentalService){
        this.movieRepository = movieRepository;
        this.rentalService = rentalService;
    }
    private int getFrequentRenterPoints(String category, int days) {
        // add bonus for two day newly release movie
        if (category.equals(MovieCategory.NEW.getCode()) && days > BONUS_THRESHOLD_DAYS) {
            frequentRenterPoints++;
        }
        return frequentRenterPoints;
    }
    //Generate movie rental
    public String statement(Customer customer) {

        var result = new StringBuilder();
        result.append(RENTAL_RECORD).append(customer.name()).append("\n");
        var movies = movieRepository.getMovies();

        for (MovieRental rental : customer.rentals()) {
            var amount =0.0;
            var rentedMovie =  Optional.ofNullable(movies.get(rental.movieId())).orElseThrow(()->new MovieNotFoundException(rental.movieId() + INVALID_MOVIE_CODE_ERROR_MESSAGE));

            // calculate amount for each movie
                amount = rentalService.getRentalObjectByCategory(rentedMovie.get().category())
                        .getPriceBasedOnCategory(rental.rentalDays());
                frequentRenterPoints++;
                frequentRenterPoints = getFrequentRenterPoints(rentedMovie.get().category(), rental.rentalDays());

                // statement for rental amount details
                result.append("\t").append(rentedMovie.get().movieId()).append("\t").append(amount).append("\n");

                totalAmount = totalAmount + amount;
        }
        // add footer lines
        result.append(Constants.AMOUNT_OWN).append(totalAmount).append("\n");
        result.append(Constants.YOU_EARNED).append(frequentRenterPoints).append(Constants.FREQUENT_POINTS).append("\n");

        return result.toString();
    }

}
