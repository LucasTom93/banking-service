package com.asc.loanservice.domain.evaluation;

import com.asc.loanservice.contracts.LoanRequestDto;

interface LoanRequestEvaluationRule {
    LoanRequestEvaluationResultDetails evaluate(LoanRequestDto loanRequestDto);
}