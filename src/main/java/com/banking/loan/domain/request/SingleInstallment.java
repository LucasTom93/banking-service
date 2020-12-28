package com.banking.loan.domain.request;

import static com.banking.loan.domain.request.LoanConstants.ANNUAL_LOAN_TAX_RATE;
import static com.banking.loan.domain.request.LoanConstants.BIG_DECIMAL_SCALE;
import static com.banking.loan.domain.request.LoanConstants.NUMBER_OF_INSTALLMENTS_IN_YEAR;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.banking.shared.domain.annotations.ValueObject;

@ValueObject
public class SingleInstallment {
    private final BigDecimal value;

    private SingleInstallment(BigDecimal value) {
        this.value = value;
    }

    static SingleInstallment of(BigDecimal loanAmount, int numberOfInstallments) {
        var singleInstallmentAmount = calculateSingleInstallmentAmount(loanAmount, numberOfInstallments);
        return new SingleInstallment(singleInstallmentAmount);
    }

    /**
     * <a href ="https://finanse.rankomat.pl/poradniki/obliczyc-rate-kredytu-gotowkowego/">Example of loan installment amount calculation (click)</a>
     */
    private static BigDecimal calculateSingleInstallmentAmount(BigDecimal loanAmount, int numberOfInstallments) {
        var sum = BigDecimal.ZERO.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        for (int i = 1; i <= numberOfInstallments; i++) {
            var percentageDividedByNumberOfInstallmentsInYear = ANNUAL_LOAN_TAX_RATE.divide(NUMBER_OF_INSTALLMENTS_IN_YEAR, BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
            var onePlusPercentageDividedByNumberOfInstallmentsInYearToThePower = BigDecimal.ONE.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP)
                    .add(percentageDividedByNumberOfInstallmentsInYear)
                    .pow(i);
            sum = sum.add(BigDecimal.ONE.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP).divide(onePlusPercentageDividedByNumberOfInstallmentsInYearToThePower, RoundingMode.HALF_UP));
        }

        return loanAmount.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP).divide(sum, RoundingMode.HALF_UP);
    }

    public String print() {
        return value.toString();
    }

    public boolean isBelowOrEqual(MonthlyIncomeThreshold monthlyIncomeThreshold) {
        return value.compareTo(monthlyIncomeThreshold.getValue()) <= 0;
    }
}