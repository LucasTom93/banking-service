package com.asc.loanservice.domain.loan;

class CustomerTaxIdVO {
    private final String value;

    private CustomerTaxIdVO(String value) {
        this.value = value;
    }

    static CustomerTaxIdVO of(String value) throws LoanValidationException {
        if (value == null) {
            throw new LoanValidationException("Provided empty customer tax id");
        }

        return new CustomerTaxIdVO(value);
    }

    String getValue() {
        return value;
    }
}