package com.banking.loan.domain.request;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import com.banking.shared.domain.annotations.ValueObject;

@ValueObject
public class CustomerAgeAfterAllInstallments {
    private final int value;

    private CustomerAgeAfterAllInstallments(int value) {
        this.value = value;
    }

    static CustomerAgeAfterAllInstallments of(int loanDurationInMonths, LocalDate firstInstallmentDate, LocalDate customerDateOfBirth) {
        var customerAgeAfterAllInstallments = calculateCustomerAgeAfterAllInstallments(loanDurationInMonths, firstInstallmentDate, customerDateOfBirth);
        return new CustomerAgeAfterAllInstallments(customerAgeAfterAllInstallments);
    }

    private static int calculateCustomerAgeAfterAllInstallments(int loanDurationInMonths, LocalDate firstInstallmentDate, LocalDate customerDateOfBirth) {
        var loanEndDate = firstInstallmentDate.plusMonths(loanDurationInMonths);
        var customerAgeAfterLoanInMonths = Period.between(customerDateOfBirth, loanEndDate).toTotalMonths();
        var monthsInYear = BigDecimal.valueOf(12);

        return BigDecimal
                .valueOf(customerAgeAfterLoanInMonths)
                .divide(monthsInYear, RoundingMode.CEILING)
                .intValue();
    }

    public boolean isHigherThan(int ageThreshold) {
        return value > ageThreshold;
    }

    public String print() {
        return String.valueOf(value);
    }
}