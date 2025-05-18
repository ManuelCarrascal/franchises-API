package com.nequi.franchise.domain.utils.constants;

public class ProductUseCaseConstants {

    public static final String ERROR_REQUIRED_FIELDS = "All product fields are required";
    public static final String ERROR_BRANCH_NOT_FOUND = "The branch does not exist";
    public static final String ERROR_PRODUCT_NOT_FOUND = "The product does not exist";
    public static final String ERROR_STOCK_VALUE = "The stock value is invalid";
    public static final String ERROR_INVALID_PRODUCT_ID_OR_NAME = "Invalid product ID or name";

    public static final Integer MIN_STOCK_VALUE = 0;

    private ProductUseCaseConstants() {
    }
}