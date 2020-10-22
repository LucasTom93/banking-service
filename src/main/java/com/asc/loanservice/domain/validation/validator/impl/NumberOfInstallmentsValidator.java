package com.asc.loanservice.domain.validation.validator.impl;

import com.asc.loanservice.annotations.DataValidator;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;

@DataValidator
class NumberOfInstallmentsValidator implements LoanRequestDataValidator {

    @Override
    public LoanRequestValidationResult validate(LoanRequestDto loanRequestDto) {
        var numberOfInstallments = loanRequestDto.getNumberOfInstallments();
        if(numberOfInstallments == null) {
            return LoanRequestValidationResult.invalid("Provided empty number of installments");
        }

        if (numberOfInstallments < 1) {
            return LoanRequestValidationResult.invalid(String.format("Number of installments must be grater than zero. Provided %s", numberOfInstallments));
        }

        return LoanRequestValidationResult.valid();
    }
}