package com.asc.loanservice.domain.loan;

import java.time.LocalDate;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class LoanRequestFactory {
    static LoanRequest createLoanRequest(LoanRequestDto loanRequestDto, LoanRequestEvaluationResult loanRequestEvaluationResult, LocalDate registrationDate) {
        return LoanRequest.Builder
                .loanRequest()
                .withLoanRequestNumber(LoanRequestNumberGenerator.generateLoanRequestNumber())
                .withLoanAmount(loanRequestDto.getLoanAmount())
                .withFirstInstallmentDate(loanRequestDto.getFirstInstallmentDate())
                .withNumberOfInstallments(loanRequestDto.getNumberOfInstallments())
                .withRegistrationDate(registrationDate)
                .withLoanRequestEvaluationResult(loanRequestEvaluationResult)
                .build();
    }
}