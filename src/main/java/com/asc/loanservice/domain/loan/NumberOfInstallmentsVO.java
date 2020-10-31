package com.asc.loanservice.domain.loan;

class NumberOfInstallmentsVO {
    private final Integer value;

    private NumberOfInstallmentsVO(Integer value) {
        this.value = value;
    }

    static NumberOfInstallmentsVO of(Integer value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty number of installments");
        }

        if (value < 1) {
            throw new LoanValidationException(String.format("Number of installments must be grater than zero. Provided %s", value));
        }

        return new NumberOfInstallmentsVO(value);
    }

    Integer getValue() {
        return value;
    }
}