package com.asc.loanservice.domain.evaluation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EvaluationData {
    private int numberOfInstallments;
    private LocalDate firstInstallmentDate;
    private LocalDate customerDateOfBirth;
    private String customerTaxId;
    private BigDecimal customerMonthlyIncome;
    private BigDecimal loanAmount;

    private EvaluationData() {
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public LocalDate getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public LocalDate getCustomerDateOfBirth() {
        return customerDateOfBirth;
    }

    public String getCustomerTaxId() {
        return customerTaxId;
    }

    public BigDecimal getCustomerMonthlyIncome() {
        return customerMonthlyIncome;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public static final class Builder {
        private int numberOfInstallments;
        private LocalDate firstInstallmentDate;
        private LocalDate customerDateOfBirth;
        private String customerTaxId;
        private BigDecimal customerMonthlyIncome;
        private BigDecimal loanAmount;

        private Builder() {
        }

        public static Builder evaluationData() {
            return new Builder();
        }

        public Builder withNumberOfInstallments(int numberOfInstallments) {
            this.numberOfInstallments = numberOfInstallments;
            return this;
        }

        public Builder withFirstInstallmentDate(LocalDate firstInstallmentDate) {
            this.firstInstallmentDate = firstInstallmentDate;
            return this;
        }

        public Builder withCustomerDateOfBirth(LocalDate customerDateOfBirth) {
            this.customerDateOfBirth = customerDateOfBirth;
            return this;
        }

        public Builder withCustomerTaxId(String customerTaxId) {
            this.customerTaxId = customerTaxId;
            return this;
        }

        public Builder withCustomerMonthlyIncome(BigDecimal customerMonthlyIncome) {
            this.customerMonthlyIncome = customerMonthlyIncome;
            return this;
        }

        public Builder withLoanAmount(BigDecimal loanAmount) {
            this.loanAmount = loanAmount;
            return this;
        }

        public EvaluationData build() {
            EvaluationData evaluationData = new EvaluationData();
            evaluationData.customerTaxId = this.customerTaxId;
            evaluationData.loanAmount = this.loanAmount;
            evaluationData.numberOfInstallments = this.numberOfInstallments;
            evaluationData.customerMonthlyIncome = this.customerMonthlyIncome;
            evaluationData.firstInstallmentDate = this.firstInstallmentDate;
            evaluationData.customerDateOfBirth = this.customerDateOfBirth;
            return evaluationData;
        }
    }
}