package com.banking.loan.application.validation;

public class LoanValidationException extends Exception {

    private final String validationMessage;

    public LoanValidationException(String validationMessage) {
        super(validationMessage);
        this.validationMessage = validationMessage;
    }

    public String getValidationMessage() {
        return validationMessage;
    }
}