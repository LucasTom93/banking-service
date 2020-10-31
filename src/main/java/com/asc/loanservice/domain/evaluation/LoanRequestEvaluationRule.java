package com.asc.loanservice.domain.evaluation;

import com.asc.loanservice.contracts.LoanRequestDto;

public interface LoanRequestEvaluationRule {
    LoanRequestEvaluationResultDetails evaluate(LoanRequestDto loanRequestDto);
}