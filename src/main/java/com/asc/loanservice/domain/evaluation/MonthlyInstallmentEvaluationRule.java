package com.asc.loanservice.domain.evaluation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Component
class MonthlyInstallmentEvaluationRule implements LoanRequestEvaluationRule {
    //All these constants could be taken from system configuration with theirs defaults
    private static final int BIG_DECIMAL_SCALE = 5;
    private static final BigDecimal PERCENTAGE_RISK_THRESHOLD = BigDecimal.valueOf(0.15);
    private static final BigDecimal NUMBER_OF_INSTALLMENTS_IN_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal ANNUAL_LOAN_TAX_RATE = BigDecimal.valueOf(0.04);

    @Override
    public LoanRequestEvaluationResultDetails evaluate(EvaluationData evaluationData) {
        var monthlyIncomePercentageThreshold = evaluationData.getCustomerMonthlyIncome().multiply(PERCENTAGE_RISK_THRESHOLD).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        var loanInstallmentAmount = calculateSingleInstallmentAmount(evaluationData);

        if (loanInstallmentAmount.compareTo(monthlyIncomePercentageThreshold) > 0) {
            return createRejectedResult(loanInstallmentAmount, evaluationData.getCustomerMonthlyIncome());
        }
        return createApprovedResult(loanInstallmentAmount, evaluationData.getCustomerMonthlyIncome());
    }

    /**
     * <a href ="https://finanse.rankomat.pl/poradniki/obliczyc-rate-kredytu-gotowkowego/">Example of loan installment amount calculation (click)</a>
     */
    private BigDecimal calculateSingleInstallmentAmount(EvaluationData evaluationData) {
        var loanAmount = evaluationData.getLoanAmount();
        var totalNumberOfInstallments = evaluationData.getNumberOfInstallments();

        var sum = BigDecimal.ZERO.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        for (int i = 1; i <= totalNumberOfInstallments; i++) {
            var percentageDividedByNumberOfInstallmentsInYear = ANNUAL_LOAN_TAX_RATE.divide(NUMBER_OF_INSTALLMENTS_IN_YEAR, BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
            var onePlusPercentageDividedByNumberOfInstallmentsInYearToThePower = BigDecimal.ONE.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP)
                    .add(percentageDividedByNumberOfInstallmentsInYear)
                    .pow(i);
            sum = sum.add(BigDecimal.ONE.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP).divide(onePlusPercentageDividedByNumberOfInstallmentsInYearToThePower, RoundingMode.HALF_UP));
        }

        return loanAmount.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP).divide(sum, RoundingMode.HALF_UP);
    }

    private LoanRequestEvaluationResultDetails createApprovedResult(BigDecimal loanInstallmentAmount, BigDecimal customerMonthlyIncome) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.APPROVED,
                String.format("Loan installment amount (%s) is acceptable for monthly customer's income (%s)", loanInstallmentAmount, customerMonthlyIncome)
        );
    }

    private LoanRequestEvaluationResultDetails createRejectedResult(BigDecimal loanInstallmentAmount, BigDecimal customerMonthlyIncome) {
        return LoanRequestEvaluationResultDetails.of(
                LoanRequestEvaluationResult.REJECTED,
                String.format("Loan installment amount (%s) is not acceptable for monthly customer's income (%s)", loanInstallmentAmount, customerMonthlyIncome)
        );
    }
}