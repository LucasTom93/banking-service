package com.asc.loanservice.domain.loan;

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