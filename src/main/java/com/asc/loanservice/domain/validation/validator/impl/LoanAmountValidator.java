package com.asc.loanservice.domain.validation.validator.impl;

import java.math.BigDecimal;

import com.asc.loanservice.annotations.DataValidator;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;

@DataValidator
class LoanAmountValidator implements LoanRequestDataValidator {

    @Override
    public LoanRequestValidationResult validate(LoanRequestDto loanRequestDto) {
        var loanAmount = loanRequestDto.getLoanAmount();
        if(loanAmount == null) {
            return LoanRequestValidationResult.invalid("Provided empty loan amount");
        }

        if (loanAmount.compareTo(BigDecimal.ZERO) < 1) {
            return LoanRequestValidationResult.invalid(String.format("Loan amount must be grater than zero. Provided %s", loanAmount));
        }

        return LoanRequestValidationResult.valid();
    }
}