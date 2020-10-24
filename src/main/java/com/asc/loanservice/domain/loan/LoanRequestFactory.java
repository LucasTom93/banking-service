package com.asc.loanservice.domain.loan;

import java.time.LocalDateTime;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class LoanRequestFactory {
    static LoanRequest createLoanRequest(LoanRequestDto loanRequestDto, LoanRequestEvaluationResult loanRequestEvaluationResult, LocalDateTime registrationDate) {
        return LoanRequest.Builder
                .loanRequest()
                .withLoanRequestNumber(LoanRequestNumberGenerator.generateLoanRequestNumber())
                .withLoanAmount(loanRequestDto.getLoanAmount())
                .withFirstInstallmentDate(loanRequestDto.getFirstInstallmentDate())
                .withNumberOfInstallments(loanRequestDto.getNumberOfInstallments())
                .withRegistrationDate(registrationDate)
                .withLoanRequestEvaluationResult(loanRequestEvaluationResult)
                .withCustomerName(loanRequestDto.getCustomerName())
                .withCustomerDateOfBirth(loanRequestDto.getCustomerBirthday())
                .withCustomerMonthlyIncome(loanRequestDto.getCustomerMonthlyIncome())
                .withCustomerTaxId(loanRequestDto.getCustomerTaxId())
                .build();
    }
}