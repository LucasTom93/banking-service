package com.banking.loan.domain.request;

import com.banking.loan.domain.evaluation.EvaluationData;
import com.banking.shared.domain.annotations.DomainFactory;

@DomainFactory
public class LoanRequestValueObjectFactory {
    public CustomerAgeAfterAllInstallments customerAgeAfterAllInstallments(EvaluationData evaluationData) {
        return CustomerAgeAfterAllInstallments.of(
                evaluationData.getNumberOfInstallments(),
                evaluationData.getFirstInstallmentDate(),
                evaluationData.getCustomerDateOfBirth()
        );
    }

    public MonthlyIncomeThreshold monthlyIncomeThreshold(EvaluationData evaluationData) {
        return MonthlyIncomeThreshold.of(evaluationData.getCustomerMonthlyIncome(), LoanConstants.PERCENTAGE_RISK_THRESHOLD);
    }

    public SingleInstallment singleInstallment(EvaluationData evaluationData) {
        return SingleInstallment.of(evaluationData.getLoanAmount(), evaluationData.getNumberOfInstallments());
    }
}