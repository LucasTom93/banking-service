package com.asc.loanservice.domain.loan;

import java.time.LocalDate;

class CustomerBirthdayVO {

    private final LocalDate value;

    private CustomerBirthdayVO(LocalDate value) {
        this.value = value;
    }

    static CustomerBirthdayVO of(LocalDate value, LocalDate currentDate) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer birthday");
        }

        if (!value.isBefore(currentDate)) {
            throw new LoanValidationException(String.format("Customer birthday must be before today. Provided %s", value));
        }

        return new CustomerBirthdayVO(value);
    }

    LocalDate getValue() {
        return value;
    }
}