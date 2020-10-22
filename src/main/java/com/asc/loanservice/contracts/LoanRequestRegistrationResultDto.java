package com.asc.loanservice.contracts;

public class LoanRequestRegistrationResultDto {
    private final String loanRequestNumber;
    private final LoanRequestEvaluationResult evaluationResult;

    private LoanRequestRegistrationResultDto(String loanRequestNumber, LoanRequestEvaluationResult evaluationResult) {
        this.loanRequestNumber = loanRequestNumber;
        this.evaluationResult = evaluationResult;
    }

    public static LoanRequestRegistrationResultDto of(String loanRequestNumber, LoanRequestEvaluationResult evaluationResult) {
        return new LoanRequestRegistrationResultDto(loanRequestNumber, evaluationResult);
    }

    public String getLoanRequestNumber() {
        return loanRequestNumber;
    }

    public LoanRequestEvaluationResult getEvaluationResult() {
        return evaluationResult;
    }
}
