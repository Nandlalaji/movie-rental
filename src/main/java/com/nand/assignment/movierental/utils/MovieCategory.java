package com.nand.assignment.movierental.utils;

public enum MovieCategory {
    /** Regular movie category. */
    REGULAR("regular", 2, 2, 1.5),
    /** New movie category. */
    NEW("new", 0, 0, 3),
    /** Children movie category. */
    CHILDREN("children", 1.5, 3, 1.5);

    private final String code;
    private final double baseFee;
    private final int defaultAllowedDays;
    private final double feeAfterAllowedDays;

    MovieCategory(
            String code, double baseFee, int defaultAllowedDays, double feeAfterAllowedDays) {
        this.code = code;
        this.baseFee = baseFee;
        this.defaultAllowedDays = defaultAllowedDays;
        this.feeAfterAllowedDays = feeAfterAllowedDays;
    }

    /**
     * Gets the movie category code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets base amount for this movie category.
     *
     * @return the base amount
     */
    public double getBaseFee() {
        return baseFee;
    }

    /**
     * Gets default allowed days to keep for these types of movies.
     *
     * @return the default allowed days
     */
    public int getDefaultAllowedDays() {
        return defaultAllowedDays;
    }

    /**
     * Gets extra days multiplier.
     *
     * @return the extra days multiplier
     */
    public double getFeeAfterAllowedDays() {
        return feeAfterAllowedDays;
    }
}
