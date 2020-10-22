package com.asc.loanservice.domain.evaluation;

import org.springframework.stereotype.Component;

import com.asc.loanservice.contracts.LoanRequestDto;

@Component
class DebtorEvaluationRule implements LoanRequestEvaluationRule {

    @Override
    public LoanRequestEvaluationResultDetails evaluate(LoanRequestDto loanRequestDto) {
        return null;
    }
}