package com.nand.assignment.movierental.service.impl;

import com.nand.assignment.movierental.service.IRentalService;
import com.nand.assignment.movierental.utils.MovieCategory;

/**
 * A service class for rental amount calculation for regular movies
 */
public class RegularMovieRental implements IRentalService {
    final MovieCategory category = MovieCategory.REGULAR;
    @Override
    public double getPriceBasedOnCategory(int days) {
        var fee  =  category.getBaseFee();
        if (days > category.getDefaultAllowedDays()) {
            return ((days - category.getDefaultAllowedDays())
                    * category.getFeeAfterAllowedDays())
                    + fee;
        }
        return fee;
    }

}
