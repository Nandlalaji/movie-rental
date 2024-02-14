package com.nand.assignment.movierental.service;
/**
 * A service interface for rental amount calculation
 */
public interface IRentalService {

    /**
     * get price based on category
     *
     * @return fee in double
     */
    double getPriceBasedOnCategory(int days);

}
