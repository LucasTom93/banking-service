package com.asc.loanservice.domain.loan;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class NumberOfInstallments {

    @Column(name = "LOAN_NUMBER_OF_INSTALLMENTS", nullable = false)
    private final Integer value;

    //For JPA
    private NumberOfInstallments() {
        value = null;
    }

    private NumberOfInstallments(Integer value) {
        this.value = value;
    }

    static NumberOfInstallments of(Integer value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty number of installments");
        }

        if (value < 1) {
            throw new LoanValidationException(String.format("Number of installments must be grater than zero. Provided %s", value));
        }

        return new NumberOfInstallments(value);
    }

    Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfInstallments that = (NumberOfInstallments) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}