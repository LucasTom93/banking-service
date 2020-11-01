package com.asc.loanservice.domain.evaluation;

import org.springframework.stereotype.Component;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Component
class DebtorEvaluationRule implements LoanRequestEvaluationRule {
    private final LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker;

    DebtorEvaluationRule(LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker) {
        this.loanDebtorRegistryCircuitBreaker = loanDebtorRegistryCircuitBreaker;
    }

    @Override
    public LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData) {
        LoanRequestEvaluationResultDetails resultDetails;
        try {
            var customerCheckResultDto = loanDebtorRegistryCircuitBreaker.checkCustomerDebtorRegistry(evaluationData.getCustomerTaxId());
            var isCustomerInDebtorList = Boolean.TRUE.equals(customerCheckResultDto.getRegisteredDebtor());
            if (isCustomerInDebtorList) {
                resultDetails = createRejectedResult();
            } else {
                resultDetails = createApprovedResult();
            }
        } catch (Exception e) {
            resultDetails = LoanRequestEvaluationResultDetails.of(
                    LoanRequestEvaluationResult.REJECTED,
                    "Could not connect to debtor-registry service"
            );
        }
        return resultDetails;
    }

    private LoanRequestEvaluationResultDetails createApprovedResult() {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.APPROVED,
                "Customer is not on the debtor list"
        );
    }

    private LoanRequestEvaluationResultDetails createRejectedResult() {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.REJECTED,
                "Customer is on the debtor list"
        );
    }
}