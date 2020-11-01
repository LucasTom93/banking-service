package com.asc.loanservice.domain.loan;

import com.asc.loanservice.annotations.DomainFactory;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationFacade;
import com.asc.loanservice.domain.time.Clock;

@DomainFactory
class LoanRequestFactory {
    private final Clock clock;
    private final LoanRequestNumberGenerator loanRequestNumberGenerator;
    private final LoanRequestEvaluationFacade loanRequestEvaluationFacade;

    LoanRequestFactory(Clock clock, LoanRequestNumberGenerator loanRequestNumberGenerator, LoanRequestEvaluationFacade loanRequestEvaluationFacade) {
        this.clock = clock;
        this.loanRequestNumberGenerator = loanRequestNumberGenerator;
        this.loanRequestEvaluationFacade = loanRequestEvaluationFacade;
    }

    LoanRequest createEvaluatedLoanRequest(LoanRequestDto loanRequestDto) throws LoanValidationException {
        var currentDate = clock.getCurrentDate();
        var loanRequestNumber = loanRequestNumberGenerator.generateLoanRequestNumber();
        var customerDateOfBirth = CustomerDateOfBirth.of(loanRequestDto.getCustomerBirthday(), currentDate);
        var loanAmount = LoanAmount.of(loanRequestDto.getLoanAmount());
        var firstInstallmentDate = FirstInstallmentDate.of(loanRequestDto.getFirstInstallmentDate(), currentDate);
        var customerTaxId = CustomerTaxId.of(loanRequestDto.getCustomerTaxId());
        var numberOfInstallments = NumberOfInstallments.of(loanRequestDto.getNumberOfInstallments());
        var customerName = CustomerName.of(loanRequestDto.getCustomerName());
        var customerMonthlyIncome = CustomerMonthlyIncome.of(loanRequestDto.getCustomerMonthlyIncome());

        var loanRequest = LoanRequest.Builder
                .loanRequest()
                .withLoanRequestNumber(loanRequestNumber)
                .withLoanAmount(loanAmount)
                .withFirstInstallmentDate(firstInstallmentDate)
                .withNumberOfInstallments(numberOfInstallments)
                .withRegistrationDate(clock.getCurrentLocalDateTime())
                .withCustomerName(customerName)
                .withCustomerDateOfBirth(customerDateOfBirth)
                .withCustomerMonthlyIncome(customerMonthlyIncome)
                .withCustomerTaxId(customerTaxId)
                .build();

        loanRequest.evaluate(loanRequestEvaluationFacade.getLoanRequestEvaluationRules());

        return loanRequest;
    }
}