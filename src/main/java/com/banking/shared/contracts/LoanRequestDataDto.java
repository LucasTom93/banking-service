package com.banking.shared.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanRequestDataDto {
    private String loanRequestNumber;
    private String customerName;
    private LocalDate customerBirthday;
    private String customerTaxId;
    private BigDecimal customerMonthlyIncome;
    private BigDecimal loanAmount;
    private Integer numberOfInstallments;
    private LocalDate firstInstallmentDate;
    private LoanRequestEvaluationResult evaluationResult;
    private LocalDateTime registrationDate;

    public String getLoanRequestNumber() {
        return loanRequestNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getCustomerBirthday() {
        return customerBirthday;
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

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public LocalDate getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public LoanRequestEvaluationResult getEvaluationResult() {
        return evaluationResult;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public static final class Builder {
        private String loanRequestNumber;
        private String customerName;
        private LocalDate customerBirthday;
        private String customerTaxId;
        private BigDecimal customerMonthlyIncome;
        private BigDecimal loanAmount;
        private Integer numberOfInstallments;
        private LocalDate firstInstallmentDate;
        private LoanRequestEvaluationResult evaluationResult;
        private LocalDateTime registrationDate;

        private Builder() {
        }

        public static Builder loanRequestDataDto() {
            return new Builder();
        }

        public Builder withLoanRequestNumber(String loanRequestNumber) {
            this.loanRequestNumber = loanRequestNumber;
            return this;
        }

        public Builder withCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder withCustomerBirthday(LocalDate customerBirthday) {
            this.customerBirthday = customerBirthday;
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

        public Builder withNumberOfInstallments(Integer numberOfInstallments) {
            this.numberOfInstallments = numberOfInstallments;
            return this;
        }

        public Builder withFirstInstallmentDate(LocalDate firstInstallmentDate) {
            this.firstInstallmentDate = firstInstallmentDate;
            return this;
        }

        public Builder withEvaluationResult(LoanRequestEvaluationResult evaluationResult) {
            this.evaluationResult = evaluationResult;
            return this;
        }

        public Builder withRegistrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public LoanRequestDataDto build() {
            LoanRequestDataDto loanRequestDataDto = new LoanRequestDataDto();
            loanRequestDataDto.registrationDate = this.registrationDate;
            loanRequestDataDto.numberOfInstallments = this.numberOfInstallments;
            loanRequestDataDto.loanRequestNumber = this.loanRequestNumber;
            loanRequestDataDto.customerName = this.customerName;
            loanRequestDataDto.loanAmount = this.loanAmount;
            loanRequestDataDto.firstInstallmentDate = this.firstInstallmentDate;
            loanRequestDataDto.evaluationResult = this.evaluationResult;
            loanRequestDataDto.customerMonthlyIncome = this.customerMonthlyIncome;
            loanRequestDataDto.customerTaxId = this.customerTaxId;
            loanRequestDataDto.customerBirthday = this.customerBirthday;
            return loanRequestDataDto;
        }
    }
}