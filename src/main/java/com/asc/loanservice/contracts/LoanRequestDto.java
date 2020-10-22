package com.asc.loanservice.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanRequestDto {
    private String customerName;
    private LocalDate customerBirthday;
    private String customerTaxId;
    private BigDecimal customerMonthlyIncome;
    private BigDecimal loanAmount;
    private Integer numberOfInstallments;
    private LocalDate firstInstallmentDate;

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

    public static final class Builder {
        private String customerName;
        private LocalDate customerBirthday;
        private String customerTaxId;
        private BigDecimal customerMonthlyIncome;
        private BigDecimal loanAmount;
        private Integer numberOfInstallments;
        private LocalDate firstInstallmentDate;

        private Builder() {
        }

        public static Builder loanRequestDto() {
            return new Builder();
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

        public LoanRequestDto build() {
            LoanRequestDto loanRequestDto = new LoanRequestDto();
            loanRequestDto.firstInstallmentDate = this.firstInstallmentDate;
            loanRequestDto.customerName = this.customerName;
            loanRequestDto.customerMonthlyIncome = this.customerMonthlyIncome;
            loanRequestDto.loanAmount = this.loanAmount;
            loanRequestDto.customerBirthday = this.customerBirthday;
            loanRequestDto.numberOfInstallments = this.numberOfInstallments;
            loanRequestDto.customerTaxId = this.customerTaxId;
            return loanRequestDto;
        }
    }
}