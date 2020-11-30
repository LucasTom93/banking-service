package com.banking.loan.application.validation;

import com.banking.shared.contracts.LoanRequestDto;

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