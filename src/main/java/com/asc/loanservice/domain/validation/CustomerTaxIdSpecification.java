package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

class CustomerTaxIdSpecification implements LoanRequestInputDataSpecification {

    @Override
    public boolean isSatisfiedBy(LoanRequestDto loanRequestDto) {
        var customerTaxId = loanRequestDto.getCustomerTaxId();
        return customerTaxId != null;
    }
}