package com.asc.loanservice.domain.loan.validation;

import java.time.LocalDate;

import com.asc.loanservice.contracts.LoanRequestDto;

class FirstInstallmentDateSpecification implements LoanRequestInputDataSpecification {

    private final LocalDate currentDate;

    FirstInstallmentDateSpecification(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean isSatisfiedBy(LoanRequestDto loanRequestDto) {
        var firstInstallmentDate = loanRequestDto.getFirstInstallmentDate();
        if (firstInstallmentDate == null) {
            return false;
        }
        return firstInstallmentDate.isAfter(currentDate);
    }
}