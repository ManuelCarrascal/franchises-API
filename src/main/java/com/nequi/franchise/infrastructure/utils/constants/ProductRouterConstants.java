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

    public static final String OPERATION_ID_DELETE = "deleteProduct";
    public static final String OPERATION_SUMMARY_DELETE = "Delete a product by ID";
    public static final String OPERATION_DESCRIPTION_DELETE = "Deletes a product using its ID";

    public static final String RESPONSE_NO_CONTENT_DESCRIPTION = "Product deleted successfully";
    public static final String RESPONSE_NOT_FOUND_DESCRIPTION = "Product not found";

    public static final String OPERATION_ID_UPDATE_STOCK = "updateProductStock";
    public static final String OPERATION_SUMMARY_UPDATE_STOCK = "Update product stock";
    public static final String OPERATION_DESCRIPTION_UPDATE_STOCK = "Modifies the stock of a specific product";
    public static final String RESPONSE_STOCK_UPDATED_DESCRIPTION = "Stock updated successfully";
    public static final String RESPONSE_INVALID_STOCK_DESCRIPTION = "Invalid stock value";
    public static final String PARAMETER_ID_NAME = "id";
    public static final String PARAMETER_ID_DESCRIPTION = "Product ID";

    private ProductRouterConstants() {
    }
}