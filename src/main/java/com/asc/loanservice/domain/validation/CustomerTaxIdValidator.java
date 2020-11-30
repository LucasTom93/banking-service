package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

class CustomerTaxIdValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerTaxId = loanRequestDto.getCustomerTaxId();
        return customerTaxId != null;
    }
}