package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Entity
@Table(name = "LOAN_REQUEST")
class LoanRequest {
    @Id
    @Column(name = "LOAN_REQUEST_NUMBER")
    private String loanRequestNumber;
    @Column(name = "LOAN_AMOUNT", nullable = false)
    private BigDecimal loanAmount;
    @Column(name = "LOAN_NUMBER_OF_INSTALLMENTS", nullable = false)
    private int numberOfInstallments;
    @Column(name = "LOAN_FIRST_INSTALLMENT_DATE", nullable = false)
    private LocalDate firstInstallmentDate;
    @Column(name = "LOAN_REGISTRATION_DATE", nullable = false)
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "LOAN_EVALUATION_RESULT", nullable = false)
    private LoanRequestEvaluationResult loanRequestEvaluationResult;
    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;
    @Column(name = "CUSTOMER_DATE_OF_BIRTH", nullable = false)
    private LocalDate customerDateOfBirth;
    @Column(name = "CUSTOMER_MONTHLY_INCOME", nullable = false)
    private BigDecimal customerMonthlyIncome;
    @Column(name = "CUSTOMER_TAX_ID", nullable = false)
    private String customerTaxId;

    String getLoanRequestNumber() {
        return loanRequestNumber;
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

    static final class Builder {
        private String loanRequestNumber;
        private BigDecimal loanAmount;
        private int numberOfInstallments;
        private LocalDate firstInstallmentDate;
        private LocalDateTime registrationDate;
        private LoanRequestEvaluationResult loanRequestEvaluationResult;
        private String customerName;
        private LocalDate customerDateOfBirth;
        private BigDecimal customerMonthlyIncome;
        private String customerTaxId;

        private Builder() {
        }

        static Builder loanRequest() {
            return new Builder();
        }

        Builder withLoanRequestNumber(String loanRequestNumber) {
            this.loanRequestNumber = loanRequestNumber;
            return this;
        }

        Builder withLoanAmount(BigDecimal loanAmount) {
            this.loanAmount = loanAmount;
            return this;
        }

        Builder withNumberOfInstallments(int numberOfInstallments) {
            this.numberOfInstallments = numberOfInstallments;
            return this;
        }

        Builder withFirstInstallmentDate(LocalDate firstInstallmentDate) {
            this.firstInstallmentDate = firstInstallmentDate;
            return this;
        }

        Builder withRegistrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        Builder withLoanRequestEvaluationResult(LoanRequestEvaluationResult loanRequestEvaluationResult) {
            this.loanRequestEvaluationResult = loanRequestEvaluationResult;
            return this;
        }

        Builder withCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        Builder withCustomerDateOfBirth(LocalDate customerDateOfBirth) {
            this.customerDateOfBirth = customerDateOfBirth;
            return this;
        }

        Builder withCustomerMonthlyIncome(BigDecimal customerMonthlyIncome) {
            this.customerMonthlyIncome = customerMonthlyIncome;
            return this;
        }

        Builder withCustomerTaxId(String customerTaxId) {
            this.customerTaxId = customerTaxId;
            return this;
        }

        LoanRequest build() {
            LoanRequest loanRequest = new LoanRequest();
            loanRequest.loanAmount = this.loanAmount;
            loanRequest.registrationDate = this.registrationDate;
            loanRequest.firstInstallmentDate = this.firstInstallmentDate;
            loanRequest.loanRequestNumber = this.loanRequestNumber;
            loanRequest.loanRequestEvaluationResult = this.loanRequestEvaluationResult;
            loanRequest.customerDateOfBirth = this.customerDateOfBirth;
            loanRequest.customerMonthlyIncome = this.customerMonthlyIncome;
            loanRequest.numberOfInstallments = this.numberOfInstallments;
            loanRequest.customerName = this.customerName;
            loanRequest.customerTaxId = this.customerTaxId;
            return loanRequest;
        }
    }
}