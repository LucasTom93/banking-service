package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class LoanAmount {

    @Column(name = "LOAN_AMOUNT", nullable = false)
    private final BigDecimal value;

    //For JPA
    private LoanAmount() {
        value = null;
    }

    private LoanAmount(BigDecimal value) {
        this.value = value;
    }

    static LoanAmount of(BigDecimal value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty loan amount");
        }

        if (value.compareTo(BigDecimal.ZERO) < 1) {
            throw new LoanValidationException(String.format("Loan amount must be grater than zero. Provided %s", value));
        }

        return new LoanAmount(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanAmount that = (LoanAmount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}