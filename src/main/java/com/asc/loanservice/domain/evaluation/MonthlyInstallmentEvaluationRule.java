package com.asc.loanservice.domain.evaluation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@Component
class MonthlyInstallmentEvaluationRule implements LoanRequestEvaluationRule {
    private static final int BIG_DECIMAL_SCALE = 5;

    @Override
    public LoanRequestEvaluationResultDetails evaluate(LoanRequestDto loanRequestDto) {
        var monthlyIncomePercentageThreshold = loanRequestDto.getCustomerMonthlyIncome().multiply(BigDecimal.valueOf(0.15)).setScale(BIG_DECIMAL_SCALE,
                RoundingMode.HALF_UP);
        var loanInstallmentAmount = calculateSingleInstallmentAmount(loanRequestDto);

        if (loanInstallmentAmount.compareTo(monthlyIncomePercentageThreshold) > 0) {
            return createRejectedResult(loanInstallmentAmount, loanRequestDto.getCustomerMonthlyIncome());
        }
        return createApprovedResult(loanInstallmentAmount, loanRequestDto.getCustomerMonthlyIncome());
    }

    /**
     * <a href ="https://finanse.rankomat.pl/poradniki/obliczyc-rate-kredytu-gotowkowego/">Example of loan installment amount calculation (click)</a>
     */
    private BigDecimal calculateSingleInstallmentAmount(LoanRequestDto loanRequestDto) {
        var loanAmount = loanRequestDto.getLoanAmount();
        var totalNumberOfInstallments = loanRequestDto.getNumberOfInstallments();
        var numberOfInstallmentsInYear = BigDecimal.valueOf(12);
        var annualLoanTaxRate = BigDecimal.valueOf(0.04);

        var sum = BigDecimal.ZERO.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        for (int i = 1; i <= totalNumberOfInstallments; i++) {
            var percentageDividedByNumberOfInstallmentsInYear = annualLoanTaxRate.divide(numberOfInstallmentsInYear, BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
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