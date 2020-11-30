package com.asc.loanservice.domain.validation;

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
                LoanRequestInputDataValidatorFactory.customerDateOfBirthValidator(currentDate),
                LoanRequestInputDataValidatorFactory.firstInstallmentDateValidator(currentDate),
                LoanRequestInputDataValidatorFactory.customerMonthlyIncomeValidator(),
                LoanRequestInputDataValidatorFactory.customerNameValidator(),
                LoanRequestInputDataValidatorFactory.customerTaxIdValidator(),
                LoanRequestInputDataValidatorFactory.loanAmountValidator(),
                LoanRequestInputDataValidatorFactory.numberOfInstallmentsValidator()
        );
        return inputDataSpecificationStream.allMatch(validator -> validator.isValid(loanRequestDto));
    }
}