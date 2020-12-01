package com.banking.loan.domain;

import com.banking.loan.api.contracts.LoanRequestDto;
import com.banking.shared.domain.annotations.DomainFactory;
import com.banking.shared.time.Clock;

@DomainFactory
public class LoanRequestFactory {
    private final Clock clock;
    private final LoanRequestNumberGenerator loanRequestNumberGenerator;

    LoanRequestFactory(Clock clock, LoanRequestNumberGenerator loanRequestNumberGenerator) {
        this.clock = clock;
        this.loanRequestNumberGenerator = loanRequestNumberGenerator;
    }

    public LoanRequest createLoanRequest(LoanRequestDto loanRequestDto) {
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