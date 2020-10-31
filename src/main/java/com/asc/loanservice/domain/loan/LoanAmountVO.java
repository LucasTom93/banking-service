package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;

class LoanAmountVO {
    private final BigDecimal value;

    public LoanAmountVO(BigDecimal value) {
        this.value = value;
    }

    static LoanAmountVO of(BigDecimal value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty loan amount");
        }

        if (value.compareTo(BigDecimal.ZERO) < 1) {
            throw new LoanValidationException(String.format("Loan amount must be grater than zero. Provided %s", value));
        }

        return new LoanAmountVO(value);
    }

    BigDecimal getValue() {
        return value;
    }
}