package com.asc.loanservice.domain.loan;

import java.time.LocalDate;

import com.asc.loanservice.contracts.LoanRequestDto;

class LoanRequestFactory {
    static LoanRequest createLoanRequest(LoanRequestDto loanRequestDto, double loanRequestTax, LocalDate registrationDate) {
        var loanRequestNumber = LoanRequestNumberGenerator.generateLoanRequestNumber();
        var numberOfInstallments = loanRequestDto.getNumberOfInstallments();
        var firstInstallmentDate = loanRequestDto.getFirstInstallmentDate();
        var loanAmount = loanRequestDto.getLoanAmount();
        return LoanRequest.of(loanRequestNumber, loanRequestTax, loanAmount, numberOfInstallments, firstInstallmentDate, registrationDate);
    }
}