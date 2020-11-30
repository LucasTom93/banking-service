package com.banking.loan.application.validation;

import java.util.stream.Stream;

import com.banking.shared.contracts.LoanRequestDto;
import com.banking.shared.domain.annotations.ApplicationService;
import com.banking.shared.time.Clock;

@ApplicationService
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