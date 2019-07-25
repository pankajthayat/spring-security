package com.practice.demoapp.modal;

public enum ErrorMessages {

    MISSING_REQUIRE_FILED("Missing required field. Please check documention for required filed"),
    RECORD_ALREADY_EXIXTS("Record already exists"),
    INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR"),
    NO_RECORD_FOUND("record not found"); ///add more

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
