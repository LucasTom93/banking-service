package com.asc.loanservice.domain.loan;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class CustomerTaxId {

    @Column(name = "CUSTOMER_TAX_ID", nullable = false)
    private final String value;

    //For JPA
    private CustomerTaxId() {
        value = null;
    }

    private CustomerTaxId(String value) {
        this.value = value;
    }

    static CustomerTaxId of(String value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer tax id");
        }

        return new CustomerTaxId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerTaxId that = (CustomerTaxId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}