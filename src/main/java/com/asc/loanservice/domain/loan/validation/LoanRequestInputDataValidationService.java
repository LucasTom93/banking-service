package com.asc.loanservice.domain.loan.validation;

import java.util.stream.Stream;

import com.asc.loanservice.annotations.DomainService;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.time.Clock;

@DomainService
public class LoanRequestInputDataValidationService {

    private final Clock clock;

    public LoanRequestInputDataValidationService(Clock clock) {
        this.clock = clock;
    }

    public boolean isValid(LoanRequestDto loanRequestDto) {
        if (loanRequestDto == null) {
            return false;
        }

        var currentDate = clock.getCurrentDate();
        var inputDataSpecificationStream = Stream.of(
                LoanRequestInputDataSpecificationFactory.customerDateOfBirthSpecification(currentDate),
                LoanRequestInputDataSpecificationFactory.firstInstallmentDateSpecification(currentDate),
                LoanRequestInputDataSpecificationFactory.customerMonthlyIncomeSpecification(),
                LoanRequestInputDataSpecificationFactory.customerNameSpecification(),
                LoanRequestInputDataSpecificationFactory.customerTaxIdSpecification(),
                LoanRequestInputDataSpecificationFactory.loanAmountSpecification(),
                LoanRequestInputDataSpecificationFactory.numberOfInstallmentsSpecification()
        );
        return inputDataSpecificationStream.allMatch(specification -> specification.isSatisfiedBy(loanRequestDto));
    }
}