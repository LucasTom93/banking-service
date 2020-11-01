package com.asc.loanservice.domain.loan;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class CustomerDateOfBirth {

    @Column(name = "CUSTOMER_DATE_OF_BIRTH", nullable = false)
    private final LocalDate value;

    //For JPA
    private CustomerDateOfBirth() {
        value = null;
    }

    private CustomerDateOfBirth(LocalDate value) {
        this.value = value;
    }

    static CustomerDateOfBirth of(LocalDate value, LocalDate currentDate) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer birthday");
        }

        if (!value.isBefore(currentDate)) {
            throw new LoanValidationException(String.format("Customer birthday must be before today. Provided %s", value));
        }

        return new CustomerDateOfBirth(value);
    }

    LocalDate getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDateOfBirth that = (CustomerDateOfBirth) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}