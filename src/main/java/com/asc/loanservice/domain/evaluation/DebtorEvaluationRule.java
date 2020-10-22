package com.asc.loanservice.domain.evaluation;

import org.springframework.stereotype.Component;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Component
class DebtorEvaluationRule implements LoanRequestEvaluationRule {
    private final LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker;

    DebtorEvaluationRule(LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker) {
        this.loanDebtorRegistryCircuitBreaker = loanDebtorRegistryCircuitBreaker;
    }

    @Override
    public LoanRequestEvaluationResultDetails evaluate(LoanRequestDto loanRequestDto) {
        try {
            var customerCheckResultDto = loanDebtorRegistryCircuitBreaker.checkCustomerDebtorRegistry(loanRequestDto.getCustomerTaxId());
            var isCustomerInDebtorList = Boolean.TRUE.equals(customerCheckResultDto.getRegisteredDebtor());
            if (isCustomerInDebtorList) {
                return createRejectedResult();
            }
            return createApprovedResult();
        } catch (Exception e) {
            return LoanRequestEvaluationResultDetails.of(
                    LoanRequestEvaluationResult.REJECTED,
                    "Could not connect to debtor-registry service"
            );
        }
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