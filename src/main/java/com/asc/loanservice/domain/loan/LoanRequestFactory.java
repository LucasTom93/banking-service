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
        var customerBirthdayVO = CustomerBirthdayVO.of(loanRequestDto.getCustomerBirthday(), currentDate);
        var loanAmountVO = LoanAmountVO.of(loanRequestDto.getLoanAmount());
        var firstInstallmentDateVO = FirstInstallmentDateVO.of(loanRequestDto.getFirstInstallmentDate(), currentDate);
        var customerTaxIdVO = CustomerTaxIdVO.of(loanRequestDto.getCustomerTaxId());
        var numberOfInstallmentsVO = NumberOfInstallmentsVO.of(loanRequestDto.getNumberOfInstallments());
        var customerNameVO = CustomerNameVO.of(loanRequestDto.getCustomerName());
        var customerMonthlyIncomeVO = CustomerMonthlyIncomeVO.of(loanRequestDto.getCustomerMonthlyIncome());

        var loanRequest = LoanRequest.Builder
                .loanRequest()
                .withLoanRequestNumber(loanRequestNumber)
                .withLoanAmount(loanAmountVO.getValue())
                .withFirstInstallmentDate(firstInstallmentDateVO.getValue())
                .withNumberOfInstallments(numberOfInstallmentsVO.getValue())
                .withRegistrationDate(clock.getCurrentLocalDateTime())
                .withCustomerName(customerNameVO.getValue())
                .withCustomerDateOfBirth(customerBirthdayVO.getValue())
                .withCustomerMonthlyIncome(customerMonthlyIncomeVO.getValue())
                .withCustomerTaxId(customerTaxIdVO.getValue())
                .build();

        loanRequest.evaluate(loanRequestDto, loanRequestEvaluationFacade.getLoanRequestEvaluationRules());

        return loanRequest;
    }
}