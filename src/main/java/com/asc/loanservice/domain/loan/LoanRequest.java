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

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Entity
class LoanRequest {
    @Id
    @Column(name = "LOAN_RESUEST_NUMBER")
    private String loanRequestNumber;
    @Column(name = "LOAN_AMOUNT", nullable = false)
    private BigDecimal loanAmount;
    @Column(name = "NUMBER_OF_INSTALLMENTS", nullable = false)
    private int numberOfInstallments;
    @Column(name = "FIRST_INSTALLMENT_DATE", nullable = false)
    private LocalDate firstInstallmentDate;
    @Column(name = "REGISTRATION_DATE", nullable = false)
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "EVALUATION_RESULT", nullable = false)
    private LoanRequestEvaluationResult loanRequestEvaluationResult;

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

    String getLoanRequestNumber() {
        return loanRequestNumber;
    }

    static final class Builder {
        private String loanRequestNumber;
        private BigDecimal loanAmount;
        private int numberOfInstallments;
        private LocalDate firstInstallmentDate;
        private LocalDateTime registrationDate;
        private LoanRequestEvaluationResult loanRequestEvaluationResult;

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

        LoanRequest build() {
            LoanRequest loanRequest = new LoanRequest();
            loanRequest.firstInstallmentDate = this.firstInstallmentDate;
            loanRequest.registrationDate = this.registrationDate;
            loanRequest.loanAmount = this.loanAmount;
            loanRequest.numberOfInstallments = this.numberOfInstallments;
            loanRequest.loanRequestEvaluationResult = this.loanRequestEvaluationResult;
            loanRequest.loanRequestNumber = this.loanRequestNumber;
            return loanRequest;
        }
    }
}