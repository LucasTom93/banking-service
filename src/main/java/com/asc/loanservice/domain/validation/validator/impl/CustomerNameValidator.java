package com.asc.loanservice.domain.validation.validator.impl;

import com.asc.loanservice.annotations.DataValidator;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;

@DataValidator
class CustomerNameValidator implements LoanRequestDataValidator {

    @Override
    public LoanRequestValidationResult validate(LoanRequestDto loanRequestDto) {
        if (loanRequestDto.getCustomerName() == null) {
            return LoanRequestValidationResult.invalid("Provided empty customer name");
        }

        return LoanRequestValidationResult.valid();
    }
}
