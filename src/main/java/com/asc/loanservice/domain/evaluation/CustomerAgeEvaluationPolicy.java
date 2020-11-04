package com.asc.loanservice.domain.evaluation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class CustomerAgeEvaluationPolicy implements LoanRequestEvaluationPolicy {
    //This constant could be taken from system configuration with its default
    private static final int CUSTOMER_AGE_THRESHOLD = 65;

    @Override
    public LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData) {
        var ageOfCustomerWhenFinishedInstallments = calculateCustomerAgeAfterAllInstallments(evaluationData);
        if (ageOfCustomerWhenFinishedInstallments > CUSTOMER_AGE_THRESHOLD) {
            return createRejectedResult(ageOfCustomerWhenFinishedInstallments);
        }
        return createApprovedResult(ageOfCustomerWhenFinishedInstallments);
    }

    private int calculateCustomerAgeAfterAllInstallments(EvaluationData evaluationData) {
        var loanDurationInMonths = evaluationData.getNumberOfInstallments();
        var firstInstallmentDate = evaluationData.getFirstInstallmentDate();
        var customerDateOfBirth = evaluationData.getCustomerDateOfBirth();
        var loanEndDate = firstInstallmentDate.plusMonths(loanDurationInMonths);
        var customerAgeAfterLoanInMonths = Period.between(customerDateOfBirth, loanEndDate).toTotalMonths();
        var monthsInYear = BigDecimal.valueOf(12);

        return BigDecimal
                .valueOf(customerAgeAfterLoanInMonths)
                .divide(monthsInYear, RoundingMode.CEILING)
                .intValue();
    }

    private LoanRequestEvaluationResultDetails createApprovedResult(int ageOfCustomerWhenFinishedInstallments) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.APPROVED,
                String.format("Customer age at the end of loan is acceptable: %s", ageOfCustomerWhenFinishedInstallments)
        );
    }

    private LoanRequestEvaluationResultDetails createRejectedResult(int ageOfCustomerWhenFinishedInstallments) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.REJECTED,
                String.format("Customer age at the end of loan is not acceptable: %s", ageOfCustomerWhenFinishedInstallments)
        );
    }
}