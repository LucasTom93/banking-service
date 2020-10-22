package com.asc.loanservice.domain.evaluation;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

public class LoanRequestEvaluationResultDetails {
    private final LoanRequestEvaluationResult loanRequestEvaluationResult;
    private final String evaluationMessage;

    private LoanRequestEvaluationResultDetails(LoanRequestEvaluationResult loanRequestEvaluationResult, String evaluationMessage) {
        this.loanRequestEvaluationResult = loanRequestEvaluationResult;
        this.evaluationMessage = evaluationMessage;
    }

    public static LoanRequestEvaluationResultDetails of(LoanRequestEvaluationResult loanRequestEvaluationResult, String evaluationMessage) {
        return new LoanRequestEvaluationResultDetails(loanRequestEvaluationResult, evaluationMessage);
    }

    public LoanRequestEvaluationResult getLoanRequestEvaluationResult() {
        return loanRequestEvaluationResult;
    }

    public String getEvaluationMessage() {
        return evaluationMessage;
    }
}