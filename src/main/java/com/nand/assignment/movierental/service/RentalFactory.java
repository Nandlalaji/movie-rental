package com.nand.assignment.movierental.service;

import com.nand.assignment.movierental.exception.UnKnownCategoryException;
import com.nand.assignment.movierental.service.impl.ChildrenMovieRental;
import com.nand.assignment.movierental.service.impl.NewMovieRental;
import com.nand.assignment.movierental.service.impl.RegularMovieRental;
import com.nand.assignment.movierental.utils.MovieCategory;

import static com.nand.assignment.movierental.utils.Constants.INVALID_MOVIE_CATEGORY_ERROR_MESSAGE;

/**
 * A service class for rental object for movie category type
 */
public class RentalFactory {

    /**
     * Gets IRentalService Object according to category type.
     *
     * @return IRentalService Object
     */
    public IRentalService getRentalObjectByCategory(String category){

        if (category.equalsIgnoreCase(MovieCategory.REGULAR.getCode())) {
            return new RegularMovieRental();
        } else if (category.equalsIgnoreCase(MovieCategory.NEW.getCode())) {
            return new NewMovieRental();
        }else if (category.equalsIgnoreCase(MovieCategory.CHILDREN.getCode())) {
            return new ChildrenMovieRental();
        }else {
            throw new UnKnownCategoryException(category + INVALID_MOVIE_CATEGORY_ERROR_MESSAGE);
        }
    }

}
