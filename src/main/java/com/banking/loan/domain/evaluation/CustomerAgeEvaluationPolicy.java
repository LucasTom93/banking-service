package com.banking.loan.domain.evaluation;

import com.banking.loan.api.contracts.LoanRequestEvaluationResult;
import com.banking.loan.domain.CustomerAgeAfterAllInstallments;
import com.banking.loan.domain.LoanRequestValueObjectFactory;

class CustomerAgeEvaluationPolicy implements LoanRequestEvaluationPolicy {
    //This constant could be taken from system configuration with its default
    private static final int CUSTOMER_AGE_THRESHOLD = 65;

    private final LoanRequestValueObjectFactory loanRequestValueObjectFactory;

    CustomerAgeEvaluationPolicy(LoanRequestValueObjectFactory loanRequestValueObjectFactory) {
        this.loanRequestValueObjectFactory = loanRequestValueObjectFactory;
    }

    @Override
    public LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData) {
        var customerAgeAfterAllInstallments = loanRequestValueObjectFactory.customerAgeAfterAllInstallments(evaluationData);
        if (customerAgeAfterAllInstallments.isHigherThan(CUSTOMER_AGE_THRESHOLD)) {
            return createRejectedResult(customerAgeAfterAllInstallments);
        }
        return createApprovedResult(customerAgeAfterAllInstallments);
    }

    private LoanRequestEvaluationResultDetails createApprovedResult(CustomerAgeAfterAllInstallments customerAgeAfterAllInstallments) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.APPROVED,
                String.format("Customer age at the end of loan is acceptable: %s", customerAgeAfterAllInstallments.print())
        );
    }

    private LoanRequestEvaluationResultDetails createRejectedResult(CustomerAgeAfterAllInstallments customerAgeAfterAllInstallments) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.REJECTED,
                String.format("Customer age at the end of loan is not acceptable: %s", customerAgeAfterAllInstallments.print())
        );
    }
}