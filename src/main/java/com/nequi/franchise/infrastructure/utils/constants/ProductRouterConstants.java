package com.nequi.franchise.infrastructure.utils.constants;

public class ProductRouterConstants {
    public static final String BASE_PATH = "/api/products";
    public static final String TAG = "Product";
    public static final String DESCRIPTION = "API for managing products";

    public static final String OPERATION_ID_CREATE = "createProduct";
    public static final String OPERATION_SUMMARY_CREATE = "Create a new product";
    public static final String OPERATION_DESCRIPTION_CREATE = "Creates a product associated to a branch";

    public static final String REQUEST_BODY_DESCRIPTION = "Product data";

    public static final String RESPONSE_OK_DESCRIPTION = "Product created successfully";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION = "Invalid product data";

    private ProductRouterConstants() {
    }
}