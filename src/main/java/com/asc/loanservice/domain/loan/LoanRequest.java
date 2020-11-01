package com.asc.loanservice.domain.loan;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asc.loanservice.annotations.DomainAggregateRoot;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationRule;

@Entity
@Table(name = "LOAN_REQUEST")
@DomainAggregateRoot
class LoanRequest {
    @Id
    @Column(name = "LOAN_REQUEST_NUMBER")
    private String loanRequestNumber;
    @Embedded
    private LoanAmount loanAmount;
    @Embedded
    private NumberOfInstallments numberOfInstallments;
    @Embedded
    private FirstInstallmentDate firstInstallmentDate;
    @Column(name = "LOAN_REGISTRATION_DATE", nullable = false)
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "LOAN_EVALUATION_RESULT", nullable = false)
    private LoanRequestEvaluationResult loanRequestEvaluationResult;
    @Embedded
    private CustomerName customerName;
    @Embedded
    private CustomerDateOfBirth customerDateOfBirth;
    @Embedded
    private CustomerMonthlyIncome customerMonthlyIncome;
    @Embedded
    private CustomerTaxId customerTaxId;

    String getLoanRequestNumber() {
        return loanRequestNumber;
    }

    LoanRequestEvaluationResult getLoanRequestEvaluationResult() {
        return loanRequestEvaluationResult;
    }

    void evaluate(LoanRequestDto loanRequestDto, Set<LoanRequestEvaluationRule> loanRequestEvaluationRules) {
        var loanRequestEvaluationDetailsSet = loanRequestEvaluationRules
                .stream()
                .map(rule -> rule.evaluate(loanRequestDto))
                .collect(Collectors.toSet());
        var areAllEvaluationRulesApproved = checkAllEvaluationRulesAreApproved(loanRequestEvaluationDetailsSet);
        loanRequestEvaluationResult = areAllEvaluationRulesApproved ? LoanRequestEvaluationResult.APPROVED : LoanRequestEvaluationResult.REJECTED;
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
        LoanRequest that = (LoanRequest) o;
        return Objects.equals(loanRequestNumber, that.loanRequestNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanRequestNumber);
    }

    static final class Builder {
        private String loanRequestNumber;
        private LoanAmount loanAmount;
        private NumberOfInstallments numberOfInstallments;
        private FirstInstallmentDate firstInstallmentDate;
        private LocalDateTime registrationDate;
        private CustomerName customerName;
        private CustomerDateOfBirth customerDateOfBirth;
        private CustomerMonthlyIncome customerMonthlyIncome;
        private CustomerTaxId customerTaxId;

        private Builder() {
        }

        static Builder loanRequest() {
            return new Builder();
        }

        Builder withLoanRequestNumber(String loanRequestNumber) {
            this.loanRequestNumber = loanRequestNumber;
            return this;
        }

        Builder withLoanAmount(LoanAmount loanAmount) {
            this.loanAmount = loanAmount;
            return this;
        }

        Builder withNumberOfInstallments(NumberOfInstallments numberOfInstallments) {
            this.numberOfInstallments = numberOfInstallments;
            return this;
        }

        Builder withFirstInstallmentDate(FirstInstallmentDate firstInstallmentDate) {
            this.firstInstallmentDate = firstInstallmentDate;
            return this;
        }

        Builder withRegistrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        Builder withCustomerName(CustomerName customerName) {
            this.customerName = customerName;
            return this;
        }

        Builder withCustomerDateOfBirth(CustomerDateOfBirth customerDateOfBirth) {
            this.customerDateOfBirth = customerDateOfBirth;
            return this;
        }

        Builder withCustomerMonthlyIncome(CustomerMonthlyIncome customerMonthlyIncome) {
            this.customerMonthlyIncome = customerMonthlyIncome;
            return this;
        }

        Builder withCustomerTaxId(CustomerTaxId customerTaxId) {
            this.customerTaxId = customerTaxId;
            return this;
        }

        LoanRequest build() {
            LoanRequest loanRequest = new LoanRequest();
            loanRequest.registrationDate = this.registrationDate;
            loanRequest.loanAmount = this.loanAmount;
            loanRequest.customerTaxId = this.customerTaxId;
            loanRequest.customerName = this.customerName;
            loanRequest.numberOfInstallments = this.numberOfInstallments;
            loanRequest.customerMonthlyIncome = this.customerMonthlyIncome;
            loanRequest.firstInstallmentDate = this.firstInstallmentDate;
            loanRequest.customerDateOfBirth = this.customerDateOfBirth;
            loanRequest.loanRequestNumber = this.loanRequestNumber;
            return loanRequest;
        }
    }
}