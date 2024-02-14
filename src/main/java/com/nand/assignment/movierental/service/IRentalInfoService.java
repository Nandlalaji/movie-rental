package com.nand.assignment.movierental.service;

import com.nand.assignment.movierental.model.Customer;
/**
 * A service interface for rental statement
 */
public interface IRentalInfoService {
    /**
     * get statement in String formate
     *
     * @return Statement
     */
    String statement(Customer customer);

}
