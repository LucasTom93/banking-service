package com.asc.loanservice.domain.loan.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

class NumberOfInstallmentsSpecification implements LoanRequestInputDataSpecification {

    @Override
    public boolean isSatisfiedBy(LoanRequestDto loanRequestDto) {
        var numberOfInstallments = loanRequestDto.getNumberOfInstallments();
        if (numberOfInstallments == null) {
            return false;
        }
        return numberOfInstallments >= 1;
    }
}