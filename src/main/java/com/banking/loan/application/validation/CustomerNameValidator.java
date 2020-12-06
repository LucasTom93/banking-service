package com.banking.loan.application.validation;

import org.apache.commons.lang.StringUtils;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerNameValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerName = loanRequestDto.getCustomerName();
        return StringUtils.isNotEmpty(customerName);
    }
}