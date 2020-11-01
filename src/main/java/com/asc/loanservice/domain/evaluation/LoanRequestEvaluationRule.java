package com.asc.loanservice.domain.evaluation;

public interface LoanRequestEvaluationRule {
    LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData);
}