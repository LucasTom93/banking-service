package com.asc.loanservice.domain.loan;

class CustomerNameVO {
    private final String value;

    private CustomerNameVO(String value) {
        this.value = value;
    }

    static CustomerNameVO of(String value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer name");
        }

        return new CustomerNameVO(value);
    }

    String getValue() {
        return value;
    }
}