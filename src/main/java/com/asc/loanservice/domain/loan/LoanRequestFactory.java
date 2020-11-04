package com.asc.loanservice.domain.loan;

import com.asc.loanservice.annotations.DomainFactory;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.time.Clock;

@DomainFactory
class LoanRequestFactory {
    private final Clock clock;
    private final LoanRequestNumberGenerator loanRequestNumberGenerator;

    LoanRequestFactory(Clock clock, LoanRequestNumberGenerator loanRequestNumberGenerator) {
        this.clock = clock;
        this.loanRequestNumberGenerator = loanRequestNumberGenerator;
    }

    LoanRequest createLoanRequest(LoanRequestDto loanRequestDto) {
        return LoanRequest.Builder
                .loanRequest()
                .withLoanRequestNumber(loanRequestNumberGenerator.generateLoanRequestNumber())
                .withLoanAmount(loanRequestDto.getLoanAmount())
                .withFirstInstallmentDate(loanRequestDto.getFirstInstallmentDate())
                .withNumberOfInstallments(loanRequestDto.getNumberOfInstallments())
                .withRegistrationDate(clock.getCurrentLocalDateTime())
                .withCustomerName(loanRequestDto.getCustomerName())
                .withCustomerDateOfBirth(loanRequestDto.getCustomerBirthday())
                .withCustomerMonthlyIncome(loanRequestDto.getCustomerMonthlyIncome())
                .withCustomerTaxId(loanRequestDto.getCustomerTaxId())
                .build();
    }
}