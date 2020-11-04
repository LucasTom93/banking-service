package com.asc.loanservice.domain.evaluation;

import com.asc.loanservice.annotations.Policy;

@Policy
public interface LoanRequestEvaluationPolicy {

    LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData);
}