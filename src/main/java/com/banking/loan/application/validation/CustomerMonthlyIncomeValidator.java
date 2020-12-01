package com.banking.loan.application.validation;

import java.math.BigDecimal;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerMonthlyIncomeValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerMonthlyIncome = loanRequestDto.getCustomerMonthlyIncome();
        if (customerMonthlyIncome == null) {
            return false;
        }
        return customerMonthlyIncome.compareTo(BigDecimal.ZERO) >= 1;
    }
}