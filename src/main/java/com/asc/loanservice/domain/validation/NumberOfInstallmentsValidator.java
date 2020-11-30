package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

class NumberOfInstallmentsValidator implements LoanRequestIncomingDataValidator {

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var numberOfInstallments = loanRequestDto.getNumberOfInstallments();
        if (numberOfInstallments == null) {
            return false;
        }
        return numberOfInstallments >= 1;
    }
}