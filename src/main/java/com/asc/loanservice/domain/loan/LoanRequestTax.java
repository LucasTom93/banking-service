package com.asc.loanservice.domain.loan;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class LoanRequestTax {
    @Id
    private String id;
    private double value;

    LoanRequestTax() {
        //for JPA
    }

    double getValue() {
        return value;
    }
}