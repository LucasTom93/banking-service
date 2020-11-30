package com.banking.loan.domain.evaluation;

import com.banking.shared.domain.annotations.Policy;

@Policy
public interface LoanRequestEvaluationPolicy {

    LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData);
}