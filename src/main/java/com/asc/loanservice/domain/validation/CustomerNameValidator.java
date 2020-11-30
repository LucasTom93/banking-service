package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

class CustomerNameValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerName = loanRequestDto.getCustomerName();
        return customerName != null;
    }
}