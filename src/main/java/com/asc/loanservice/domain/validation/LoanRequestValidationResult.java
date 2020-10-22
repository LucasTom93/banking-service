package com.asc.loanservice.domain.validation;

public class LoanRequestValidationResult {
    private final boolean isValid;
    private final String validationMessage;

    private LoanRequestValidationResult(boolean isValid, String validationMessage) {
        this.isValid = isValid;
        this.validationMessage = validationMessage;
    }

    public static LoanRequestValidationResult valid() {
        return new LoanRequestValidationResult(true, "");
    }

    public static LoanRequestValidationResult invalid(String validationMessage) {
        return new LoanRequestValidationResult(false, validationMessage);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getValidationMessage() {
        return validationMessage;
    }
}