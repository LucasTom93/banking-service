package com.asc.loanservice.domain.validation;

import java.time.LocalDate;

import com.asc.loanservice.contracts.LoanRequestDto;

class CustomerDateOfBirthValidator implements LoanRequestIncomingDataValidator {

    private final LocalDate currentDate;

    CustomerDateOfBirthValidator(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean isValid(LoanRequestDto loanRequestDto) {
        var customerDateOfBirth = loanRequestDto.getCustomerBirthday();
        if (customerDateOfBirth == null) {
            return false;
        }
        return customerDateOfBirth.isBefore(currentDate);
    }
}