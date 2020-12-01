package com.banking.loan.application.validation;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerNameValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerName = loanRequestDto.getCustomerName();
        return customerName != null;
    }
}