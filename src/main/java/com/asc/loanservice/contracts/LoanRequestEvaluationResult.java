package com.asc.loanservice.contracts;

public enum LoanRequestEvaluationResult {
    APPROVED,
    REJECTED;

    public static LoanRequestEvaluationResult fromString(String vale) {
        return APPROVED.name().equals(vale) ? APPROVED : REJECTED;
    }
}