package com.asc.loanservice.domain.evaluation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;

import org.springframework.stereotype.Component;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Component
class CustomerAgeEvaluationRule implements LoanRequestEvaluationRule {
    private static final int CUSTOMER_AGE_THRESHOLD = 65;

    @Override
    public LoanRequestEvaluationResultDetails evaluate(LoanRequestDto loanRequestDto) {
        var ageOfCustomerWhenFinishedInstallments = calculateCustomerAgeAfterAllInstallments(loanRequestDto);
        if (ageOfCustomerWhenFinishedInstallments > CUSTOMER_AGE_THRESHOLD) {
            return createRejectedResult(ageOfCustomerWhenFinishedInstallments);
        }
        return createApprovedResult(ageOfCustomerWhenFinishedInstallments);
    }

    private int calculateCustomerAgeAfterAllInstallments(LoanRequestDto loanRequestDto) {
        var loanDurationInMonths = loanRequestDto.getNumberOfInstallments();
        var firstInstallmentDate = loanRequestDto.getFirstInstallmentDate();
        var customerBirthday = loanRequestDto.getCustomerBirthday();
        var loanEndDate = firstInstallmentDate.plusMonths(loanDurationInMonths);
        var customerAgeAfterLoanInMonths = Period.between(customerBirthday, loanEndDate).toTotalMonths();

        return BigDecimal
                .valueOf(customerAgeAfterLoanInMonths)
                .divide(BigDecimal.valueOf(12), RoundingMode.CEILING)
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