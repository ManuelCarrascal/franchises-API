package com.nequi.franchise.infrastructure.utils.constants;


public class StockQueryRouterConstants {

    public static final String BASE_PATH = "/api/franchises/{id}/top-stock-products";
    public static final String TAG = "Stock";
    public static final String DESCRIPTION = "API for querying top-stock products per branch in a franchise";

    public static final String OPERATION_ID = "getTopStockProducts";
    public static final String OPERATION_SUMMARY = "Get product with highest stock per branch";
    public static final String OPERATION_DESCRIPTION = "Returns the product with the highest stock in each branch for a given franchise";

    public static final String PARAM_ID_NAME = "id";
    public static final String PARAM_ID_DESCRIPTION = "Franchise ID";

    public static final String RESPONSE_OK_DESCRIPTION = "Top stock products retrieved successfully";
    public static final String RESPONSE_NOT_FOUND_DESCRIPTION = "Franchise not found";
    public static final String RESPONSE_ERROR_DESCRIPTION = "Internal server error";

    private StockQueryRouterConstants() {
    }
}