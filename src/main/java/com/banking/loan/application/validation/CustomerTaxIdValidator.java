package com.banking.loan.application.validation;

import com.banking.shared.contracts.LoanRequestDto;

class CustomerTaxIdValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerTaxId = loanRequestDto.getCustomerTaxId();
        return customerTaxId != null;
    }
}