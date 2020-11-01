package com.asc.loanservice.domain.loan;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class FirstInstallmentDate {

    @Column(name = "LOAN_FIRST_INSTALLMENT_DATE", nullable = false)
    private final LocalDate value;

    //For JPA
    private FirstInstallmentDate() {
        value = null;
    }

    private FirstInstallmentDate(LocalDate value) {
        this.value = value;
    }

    static FirstInstallmentDate of(LocalDate value, LocalDate currentDate) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty first installment date");
        }

        if (!value.isAfter(currentDate)) {
            throw new LoanValidationException(String.format("First installment date must be after today (%s). Provided %s", currentDate, value));
        }
        return new FirstInstallmentDate(value);
    }

    LocalDate getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstInstallmentDate that = (FirstInstallmentDate) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}