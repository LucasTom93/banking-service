package com.banking.loan.domain.evaluation;

import java.math.BigDecimal;

import com.banking.loan.api.contracts.LoanRequestEvaluationResult;
import com.banking.loan.domain.request.LoanRequestValueObjectFactory;
import com.banking.loan.domain.request.SingleInstallment;

class MonthlyInstallmentEvaluationPolicy implements LoanRequestEvaluationPolicy {

    private final LoanRequestValueObjectFactory loanRequestValueObjectFactory;

    MonthlyInstallmentEvaluationPolicy(LoanRequestValueObjectFactory loanRequestValueObjectFactory) {
        this.loanRequestValueObjectFactory = loanRequestValueObjectFactory;
    }

    @Override
    public LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData) {

        var monthlyIncomeThreshold = loanRequestValueObjectFactory.monthlyIncomeThreshold(evaluationData);
        var singleInstallment = loanRequestValueObjectFactory.singleInstallment(evaluationData);

        if (!singleInstallment.isBelowOrEqual(monthlyIncomeThreshold)) {
            return createRejectedResult(singleInstallment, evaluationData.getCustomerMonthlyIncome());
        }
        return createApprovedResult(singleInstallment, evaluationData.getCustomerMonthlyIncome());
    }

    private LoanRequestEvaluationResultDetails createApprovedResult(SingleInstallment singleInstallment, BigDecimal customerMonthlyIncome) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.APPROVED,
                String.format("Loan installment amount (%s) is acceptable for monthly customer's income (%s)", singleInstallment.print(), customerMonthlyIncome)
        );
    }

    private LoanRequestEvaluationResultDetails createRejectedResult(SingleInstallment singleInstallment, BigDecimal customerMonthlyIncome) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.REJECTED,
                String.format("Loan installment amount (%s) is not acceptable for monthly customer's income (%s)", singleInstallment.print(), customerMonthlyIncome)
        );
    }
}