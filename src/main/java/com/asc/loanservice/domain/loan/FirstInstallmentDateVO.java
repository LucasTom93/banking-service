package com.asc.loanservice.domain.loan;

import java.time.LocalDate;

class FirstInstallmentDateVO {
    private final LocalDate value;

    private FirstInstallmentDateVO(LocalDate value) {
        this.value = value;
    }

    static FirstInstallmentDateVO of(LocalDate value, LocalDate currentDate) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty first installment date");
        }

        if (!value.isAfter(currentDate)) {
            throw new LoanValidationException(String.format("First installment date must be before today. Provided %s", currentDate));
        }
        return new FirstInstallmentDateVO(value);
    }

    LocalDate getValue() {
        return value;
    }
}