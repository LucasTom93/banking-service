package com.asc.loanservice.domain.validation;

import java.time.LocalDate;

import com.asc.loanservice.contracts.LoanRequestDto;

class CustomerDateOfBirthSpecification implements LoanRequestInputDataSpecification {

    private final LocalDate currentDate;

    CustomerDateOfBirthSpecification(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean isSatisfiedBy(LoanRequestDto loanRequestDto) {
        var customerDateOfBirth = loanRequestDto.getCustomerBirthday();
        if (customerDateOfBirth == null) {
            return false;
        }
        return customerDateOfBirth.isBefore(currentDate);
    }
}