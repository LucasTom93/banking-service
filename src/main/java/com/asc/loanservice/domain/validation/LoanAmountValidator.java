package com.asc.loanservice.domain.validation;

import java.math.BigDecimal;

import com.asc.loanservice.contracts.LoanRequestDto;

class LoanAmountValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var loanAmount = loanRequestDto.getLoanAmount();
        if (loanAmount == null) {
            return false;
        }
        return loanAmount.compareTo(BigDecimal.ZERO) >= 1;
    }
}