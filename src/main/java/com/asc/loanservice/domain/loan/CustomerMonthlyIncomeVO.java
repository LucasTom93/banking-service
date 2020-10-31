package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;

class CustomerMonthlyIncomeVO {
    private final BigDecimal value;

    private CustomerMonthlyIncomeVO(BigDecimal value) {
        this.value = value;
    }

    static CustomerMonthlyIncomeVO of(BigDecimal value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer monthly income");
        }

        if (value.compareTo(BigDecimal.ZERO) < 1) {
            throw new LoanValidationException(String.format("Customer monthly income must be grater than zero. Provided %s", value));
        }
        return new CustomerMonthlyIncomeVO(value);
    }

    BigDecimal getValue() {
        return value;
    }
}