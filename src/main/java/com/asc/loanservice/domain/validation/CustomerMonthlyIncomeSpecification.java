package com.asc.loanservice.domain.validation;

import java.math.BigDecimal;

import com.asc.loanservice.contracts.LoanRequestDto;

class CustomerMonthlyIncomeSpecification implements LoanRequestInputDataSpecification {

    @Override
    public boolean isSatisfiedBy(LoanRequestDto loanRequestDto) {
        var customerMonthlyIncome = loanRequestDto.getCustomerMonthlyIncome();
        if (customerMonthlyIncome == null) {
            return false;
        }
        return customerMonthlyIncome.compareTo(BigDecimal.ZERO) >= 1;
    }
}