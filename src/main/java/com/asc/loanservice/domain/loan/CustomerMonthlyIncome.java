package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class CustomerMonthlyIncome {

    @Column(name = "CUSTOMER_MONTHLY_INCOME", nullable = false)
    private final BigDecimal value;

    //For JPA
    private CustomerMonthlyIncome() {
        value = null;
    }

    private CustomerMonthlyIncome(BigDecimal value) {
        this.value = value;
    }

    static CustomerMonthlyIncome of(BigDecimal value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer monthly income");
        }

        if (value.compareTo(BigDecimal.ZERO) < 1) {
            throw new LoanValidationException(String.format("Customer monthly income must be grater than zero. Provided %s", value));
        }
        return new CustomerMonthlyIncome(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerMonthlyIncome that = (CustomerMonthlyIncome) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}