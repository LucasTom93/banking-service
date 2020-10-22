package com.asc.loanservice.domain.validation.validator.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.time.Clock;
import com.asc.loanservice.domain.validation.LoanRequestDataValidator;

class LoanValidationFacadeTestDataProvider {
    private final Clock testClock = Clock.getDefault();

    LoanRequestDto createCorrectLoanRequestDto() {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerBirthday(testClock.getCurrentDate().minusDays(10))
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withFirstInstallmentDate(testClock.getCurrentDate().plusMonths(1))
                .withNumberOfInstallments(10)
                .withCustomerTaxId("1234")
                .withLoanAmount(BigDecimal.valueOf(1000))
                .build();
    }

    LoanRequestDto createLoanRequestDtoWithIncorrectNumericValues() {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerBirthday(testClock.getCurrentDate().minusDays(10))
                .withCustomerMonthlyIncome(BigDecimal.ZERO)
                .withFirstInstallmentDate(testClock.getCurrentDate().plusMonths(1))
                .withNumberOfInstallments(0)
                .withCustomerTaxId("1234")
                .withLoanAmount(BigDecimal.ZERO)
                .build();
    }

    LoanRequestDto createLoanRequestDtoWithIncorrectDateValues() {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerBirthday(testClock.getCurrentDate().plusDays(1L))
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withFirstInstallmentDate(testClock.getCurrentDate().minusMonths(1L))
                .withNumberOfInstallments(10)
                .withCustomerTaxId("1234")
                .withLoanAmount(BigDecimal.valueOf(1000))
                .build();
    }

    LoanRequestDto createEmptyLoanRequestDto() {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .build();
    }

    Set<LoanRequestDataValidator> getDateLoanRequestDataValidators() {
        return Set.of(
                new CustomerBirthdayValidator(testClock),
                new FirstInstallmentsDateValidator(testClock)
        );
    }

    Set<LoanRequestDataValidator> getNumericLoanRequestDataValidators() {
        return Set.of(
                new LoanAmountValidator(),
                new NumberOfInstallmentsValidator(),
                new CustomerMonthlyIncomeValidator()
        );
    }

    Set<LoanRequestDataValidator> getTextLoanRequestDataValidators() {
        return Set.of(
                new CustomerNameValidator(),
                new CustomerTaxIdValidator()
        );
    }

    Set<LoanRequestDataValidator> getAllLoanRequestDataValidators() {
        Set<LoanRequestDataValidator> allLoanRequestDataValidators = new HashSet<>();
        allLoanRequestDataValidators.addAll(getDateLoanRequestDataValidators());
        allLoanRequestDataValidators.addAll(getTextLoanRequestDataValidators());
        allLoanRequestDataValidators.addAll(getNumericLoanRequestDataValidators());
        return allLoanRequestDataValidators;
    }
}