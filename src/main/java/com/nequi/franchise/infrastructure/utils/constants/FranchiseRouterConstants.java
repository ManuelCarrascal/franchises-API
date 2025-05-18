package com.nequi.franchise.infrastructure.utils.constants;

public class FranchiseRouterConstants {

    public static final String BASE_PATH = "/api/franchises";
    public static final String TAG = "Franchise";
    public static final String DESCRIPTION = "API for franchise management";
    public static final String OPERATION_ID_CREATE = "createFranchise";
    public static final String OPERATION_SUMMARY_CREATE = "Create a new franchise";
    public static final String OPERATION_DESCRIPTION_CREATE = "Registers a new franchise with the provided name";
    public static final String REQUEST_BODY_DESCRIPTION = "Franchise data to be created";
    public static final String RESPONSE_OK_DESCRIPTION = "Franchise successfully created";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION = "Invalid input data";

    private FranchiseRouterConstants() {
    }
}
