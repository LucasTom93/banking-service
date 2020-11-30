package com.banking.loan.application.validation;

import java.time.LocalDate;

import com.banking.shared.contracts.LoanRequestDto;

class FirstInstallmentDateValidator implements LoanRequestIncomingDataValidator {

    private final LocalDate currentDate;

    FirstInstallmentDateValidator(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var firstInstallmentDate = loanRequestDto.getFirstInstallmentDate();
        if (firstInstallmentDate == null) {
            return false;
        }
        return firstInstallmentDate.isAfter(currentDate);
    }
}