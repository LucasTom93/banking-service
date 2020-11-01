package com.asc.loanservice.domain.loan;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class CustomerName {

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private final String value;

    //For JPA
    private CustomerName() {
        value = null;
    }

    private CustomerName(String value) {
        this.value = value;
    }

    static CustomerName of(String value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer name");
        }

        return new CustomerName(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerName that = (CustomerName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}