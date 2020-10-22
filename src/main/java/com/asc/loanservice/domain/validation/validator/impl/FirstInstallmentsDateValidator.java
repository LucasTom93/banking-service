package com.asc.loanservice.domain.validation.validator.impl;

import com.asc.loanservice.annotations.DataValidator;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.time.Clock;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;

@DataValidator
class FirstInstallmentsDateValidator implements LoanRequestDataValidator {
    private final Clock clock;

    FirstInstallmentsDateValidator(Clock clock) {
        this.clock = clock;
    }

    @Override
    public LoanRequestValidationResult validate(LoanRequestDto loanRequestDto) {
        var firstInstallmentDate = loanRequestDto.getFirstInstallmentDate();
        if (firstInstallmentDate == null) {
            return LoanRequestValidationResult.invalid("Provided empty first installment date");
        }

        if (!firstInstallmentDate.isAfter(clock.getCurrentDate())) {
            return LoanRequestValidationResult.invalid(String.format("First installment date must be before today. Provided %s", firstInstallmentDate));
        }

        return LoanRequestValidationResult.valid();
    }
}