package com.asc.loanservice.domain.loan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class LoanRequest {
    @Id
    @Column(name = "LOAN_RESUEST_NUMBER")
    private String loanRequestNumber;
    @Column(name = "LOAN_REQUEST_TAX_ID", nullable = false)
    private double loanRequestTax;

    LoanRequest() {
        //for JPA
    }

    private LoanRequest(String loanRequestNumber, double loanRequestTax) {
        this.loanRequestNumber = loanRequestNumber;
        this.loanRequestTax = loanRequestTax;
    }

    static LoanRequest of(String loanRequestNumber, double loanRequestTax) {
        return new LoanRequest(loanRequestNumber, loanRequestTax);
    }
}
