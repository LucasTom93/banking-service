package com.asc.loanservice.domain.validation.validator.impl;

import com.asc.loanservice.annotations.DataValidator;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;

@DataValidator
class CustomerTaxIdValidator implements LoanRequestDataValidator {

    @Override
    public LoanRequestValidationResult validate(LoanRequestDto loanRequestDto) {
        if (loanRequestDto.getCustomerTaxId() == null) {
            return LoanRequestValidationResult.invalid("Provided empty customer tax id");
        }
        return LoanRequestValidationResult.valid();
    }
}