package com.banking.loan.domain;

import com.banking.loan.api.contracts.LoanRequestDto;
import com.banking.shared.domain.annotations.DomainFactory;
import com.banking.shared.identity.IdentityGenerator;
import com.banking.shared.time.Clock;

@DomainFactory
public class LoanRequestFactory {
    private final Clock clock;
    private final IdentityGenerator identityGenerator;

    LoanRequestFactory(Clock clock, IdentityGenerator identityGenerator) {
        this.clock = clock;
        this.identityGenerator = identityGenerator;
    }

    public LoanRequest createLoanRequest(LoanRequestDto loanRequestDto) {
        return LoanRequest.Builder
                .loanRequest()
                .withLoanRequestNumber(identityGenerator.generateStringId())
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