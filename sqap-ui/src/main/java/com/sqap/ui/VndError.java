package com.sqap.ui;

public class VndError {
    private final String logref;
    private final String errorMessage;

    public VndError(String logref, String errorMessage) {
        this.logref = logref;
        this.errorMessage = errorMessage;
    }
}
