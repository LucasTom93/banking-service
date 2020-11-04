package com.asc.loanservice.domain.evaluation;

public interface LoanRequestEvaluationPolicy {

    LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData);
}