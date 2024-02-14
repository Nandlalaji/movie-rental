package com.nand.assignment.movierental.service.impl;

import com.nand.assignment.movierental.service.IRentalService;
import com.nand.assignment.movierental.utils.MovieCategory;

/**
 * A service class for rental amount calculation for new movies
 */
public class NewMovieRental implements IRentalService {

    final MovieCategory category = MovieCategory.NEW;
    @Override
    public double getPriceBasedOnCategory(int days) {
        return days * category.getFeeAfterAllowedDays();
    }

}
