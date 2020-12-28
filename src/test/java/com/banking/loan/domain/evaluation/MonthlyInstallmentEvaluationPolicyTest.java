package com.banking.loan.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestEvaluationResult;
import com.banking.loan.domain.request.LoanRequestValueObjectFactory;

class MonthlyInstallmentEvaluationPolicyTest {
    private MonthlyInstallmentEvaluationPolicy monthlyInstallmentEvaluationRule = new MonthlyInstallmentEvaluationPolicy(new LoanRequestValueObjectFactory());

    @Test
    void shouldApproveLoanRequestWhenReachedAgeThresholdAcceptable() {
        //given
        var loanAmount = BigDecimal.valueOf(40000);
        var numberOfInstallments = 30;
        var monthlyIncome = BigDecimal.valueOf(10000);

        //when
        var evaluationResultDetails = monthlyInstallmentEvaluationRule.evaluate(createEvaluationData(loanAmount, numberOfInstallments, monthlyIncome));

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    @Test
    void shouldRejectLoanRequestWhenReachedAgeThresholdIsNotAcceptable() {
        //given
        var loanAmount = BigDecimal.valueOf(12000);
        var numberOfInstallments = 10;
        var monthlyIncome = BigDecimal.valueOf(5000);

        //when
        var evaluationResultDetails = monthlyInstallmentEvaluationRule.evaluate(createEvaluationData(loanAmount, numberOfInstallments, monthlyIncome));

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    private EvaluationData createEvaluationData(BigDecimal loanAmount, int numberOfInstallments, BigDecimal monthlyIncome) {
        return EvaluationData.Builder
                .evaluationData()
                .withCustomerMonthlyIncome(monthlyIncome)
                .withNumberOfInstallments(numberOfInstallments)
                .withLoanAmount(loanAmount)
                .build();
    }
}