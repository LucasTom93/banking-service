package com.asc.loanservice.domain.validation.validator.impl;

import java.math.BigDecimal;

import com.asc.loanservice.annotations.DataValidator;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;

@DataValidator
class CustomerMonthlyIncomeValidator implements LoanRequestDataValidator {

    @Override
    public LoanRequestValidationResult validate(LoanRequestDto loanRequestDto) {
        var customerMonthlyIncome = loanRequestDto.getCustomerMonthlyIncome();
        if (customerMonthlyIncome == null) {
            return LoanRequestValidationResult.invalid("Provided empty customer monthly income");
        }

        if (customerMonthlyIncome.compareTo(BigDecimal.ZERO) < 1) {
            return LoanRequestValidationResult.invalid(String.format("Customer monthly income must be grater than zero. Provided %s", customerMonthlyIncome));
        }

        return LoanRequestValidationResult.valid();
    }
}