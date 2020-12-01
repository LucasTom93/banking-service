package com.banking.loan.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.banking.loan.api.contracts.LoanRequestEvaluationResult;
import com.banking.loan.domain.evaluation.EvaluationData;
import com.banking.loan.domain.evaluation.LoanRequestEvaluationPolicy;
import com.banking.loan.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.banking.shared.domain.annotations.DomainAggregateRoot;

@Entity
@Table(name = "LOAN_REQUEST")
@DomainAggregateRoot
public class LoanRequest {
    @Id
    @Column(name = "LOAN_REQUEST_NUMBER")
    private String loanRequestNumber;
    @Column(name = "LOAN_AMOUNT", nullable = false)
    private BigDecimal loanAmount;
    @Column(name = "LOAN_NUMBER_OF_INSTALLMENTS", nullable = false)
    private Integer numberOfInstallments;
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

    public String getLoanRequestNumber() {
        return loanRequestNumber;
    }

    public LoanRequestEvaluationResult getLoanRequestEvaluationResult() {
        return loanRequestEvaluationResult;
    }

    public void evaluate(Set<LoanRequestEvaluationPolicy> loanRequestEvaluationPolicies) {
        var evaluationData = prepareEvaluationData();
        var loanRequestEvaluationDetailsSet = loanRequestEvaluationPolicies
                .stream()
                .map(rule -> rule.evaluate(evaluationData))
                .collect(Collectors.toSet());
        var areAllEvaluationRulesApproved = checkAllEvaluationRulesAreApproved(loanRequestEvaluationDetailsSet);
        loanRequestEvaluationResult = areAllEvaluationRulesApproved ? LoanRequestEvaluationResult.APPROVED : LoanRequestEvaluationResult.REJECTED;
    }

    private EvaluationData prepareEvaluationData() {
        return EvaluationData.Builder
                .evaluationData()
                .withCustomerDateOfBirth(customerDateOfBirth)
                .withCustomerMonthlyIncome(customerMonthlyIncome)
                .withCustomerTaxId(customerTaxId)
                .withFirstInstallmentDate(firstInstallmentDate)
                .withLoanAmount(loanAmount)
                .withNumberOfInstallments(numberOfInstallments)
                .build();
    }

    private boolean checkAllEvaluationRulesAreApproved(Set<LoanRequestEvaluationResultDetails> loanRequestEvaluationDetailsSet) {
        return loanRequestEvaluationDetailsSet
                .stream()
                .map(LoanRequestEvaluationResultDetails::getLoanRequestEvaluationResult)
                .allMatch(evaluationResult -> evaluationResult.equals(LoanRequestEvaluationResult.APPROVED));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanRequest)) return false;
        var that = (LoanRequest) o;
        return Objects.equals(loanRequestNumber, that.loanRequestNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanRequestNumber);
    }

    static final class Builder {
        private String loanRequestNumber;
        private BigDecimal loanAmount;
        private Integer numberOfInstallments;
        private LocalDate firstInstallmentDate;
        private LocalDateTime registrationDate;
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

        Builder withNumberOfInstallments(Integer numberOfInstallments) {
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
            loanRequest.registrationDate = this.registrationDate;
            loanRequest.loanAmount = this.loanAmount;
            loanRequest.customerTaxId = this.customerTaxId;
            loanRequest.numberOfInstallments = this.numberOfInstallments;
            loanRequest.customerName = this.customerName;
            loanRequest.customerMonthlyIncome = this.customerMonthlyIncome;
            loanRequest.firstInstallmentDate = this.firstInstallmentDate;
            loanRequest.customerDateOfBirth = this.customerDateOfBirth;
            loanRequest.loanRequestNumber = this.loanRequestNumber;
            return loanRequest;
        }
    }
}