package com.banking.loan.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.banking.shared.domain.annotations.ValueObject;

@ValueObject
public class MonthlyIncomeThreshold {
    private final BigDecimal value;

    private MonthlyIncomeThreshold(BigDecimal value) {
        this.value = value;
    }

    static MonthlyIncomeThreshold of(BigDecimal monthlyIncome, BigDecimal percentageRiskThreshold) {
        var monthlyIncomePercentageThreshold = calculateMonthlyIncomePercentageThreshold(monthlyIncome, percentageRiskThreshold);
        return new MonthlyIncomeThreshold(monthlyIncomePercentageThreshold);
    }

    private static BigDecimal calculateMonthlyIncomePercentageThreshold(BigDecimal monthlyIncome, BigDecimal percentageRiskThreshold) {
        return monthlyIncome.multiply(percentageRiskThreshold).setScale(LoanConstants.BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    BigDecimal getValue() {
        return value;
    }
}