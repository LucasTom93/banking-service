package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class LoanRequest {
    @Id
    @Column(name = "LOAN_RESUEST_NUMBER")
    private String loanRequestNumber;
    @Column(name = "LOAN_REQUEST_TAX_ID", nullable = false)
    private Double loanRequestTax;
    @Column(name = "LOAN_AMOUNT", nullable = false)
    private BigDecimal loanAmount;
    @Column(name = "NUMBER_OF_INSTALLMENTS", nullable = false)
    private int numberOfInstallments;
    @Column(name = "FIRST_INSTALLMENT_DATE", nullable = false)
    private LocalDate firstInstallmentDate;
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private LocalDate registrationDate;

    LoanRequest() {
        //for JPA
    }

    private LoanRequest(String loanRequestNumber, double loanRequestTax, BigDecimal loanAmount, int numberOfInstallments, LocalDate firstInstallmentDate, LocalDate registrationDate) {
        this.loanRequestNumber = loanRequestNumber;
        this.loanRequestTax = loanRequestTax;
        this.loanAmount = loanAmount;
        this.numberOfInstallments = numberOfInstallments;
        this.firstInstallmentDate = firstInstallmentDate;
    }

    static LoanRequest of(String loanRequestNumber, double loanRequestTax, BigDecimal loanAmount, int numberOfInstallments, LocalDate firstInstallmentDate, LocalDate registrationDate) {
        return new LoanRequest(loanRequestNumber, loanRequestTax, loanAmount, numberOfInstallments, firstInstallmentDate, registrationDate);
    }

    public String getLoanRequestNumber() {
        return loanRequestNumber;
    }

    public double getLoanRequestTax() {
        return loanRequestTax;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public LocalDate getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanRequest)) return false;
        LoanRequest that = (LoanRequest) o;
        return Objects.equals(loanRequestNumber, that.loanRequestNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanRequestNumber);
    }
}