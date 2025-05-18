package com.nequi.franchise.infrastructure.utils.constants;

public class BranchRouterConstants {

    public static final String BASE_PATH = "/api/branches";
    public static final String TAG = "Branch";
    public static final String DESCRIPTION = "API for managing branches";

    public static final String OPERATION_ID_CREATE = "createBranch";
    public static final String OPERATION_SUMMARY_CREATE = "Create a new branch";
    public static final String OPERATION_DESCRIPTION_CREATE = "Creates a new branch for a given franchise";

    public static final String REQUEST_BODY_DESCRIPTION = "Branch data";

    public static final String RESPONSE_OK_DESCRIPTION = "Branch successfully created";
    public static final String RESPONSE_BAD_REQUEST_DESCRIPTION = "Invalid branch data";
    public static final String RESPONSE_NOT_FOUND_DESCRIPTION = "Franchise not found";

    public static final String OPERATION_ID_UPDATE = "updateBranch";
    public static final String OPERATION_SUMMARY_UPDATE = "Update a branch's name";
    public static final String OPERATION_DESCRIPTION_UPDATE = "Updates the name of a specific branch by ID";
    public static final String RESPONSE_OK_UPDATE_DESCRIPTION = "Branch updated successfully";
    public static final String RESPONSE_INVALID_INPUT_DESCRIPTION = "Invalid input";
    public static final String RESPONSE_BRANCH_NOT_FOUND_DESCRIPTION = "Branch not found";

    public static final String PARAMETER_ID_NAME = "id";
    public static final String PARAMETER_ID_DESCRIPTION = "Branch ID";

    private BranchRouterConstants() {
    }
}
