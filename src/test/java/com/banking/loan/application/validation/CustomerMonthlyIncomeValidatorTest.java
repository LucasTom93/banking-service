package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerMonthlyIncomeValidatorTest {

    private final CustomerMonthlyIncomeValidator customerMonthlyIncomeValidator = new CustomerMonthlyIncomeValidator();

    @Test
    void shouldPassValidationWhenCustomerMonthlyIncomeIsGreaterThanZero() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerMonthlyIncome(new BigDecimal(10000));

        //when
        var customerMonthlyIncomeIsValid = customerMonthlyIncomeValidator.isValid(loanRequestDto);

        //then
        assertThat(customerMonthlyIncomeIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWhenCustomerMonthlyIncomeIsEmpty() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerMonthlyIncome(null);

        //when
        var customerMonthlyIncomeIsValid = customerMonthlyIncomeValidator.isValid(loanRequestDto);

        //then
        assertThat(customerMonthlyIncomeIsValid).isFalse();
    }

    @Test
    void shouldNotPassValidationWhenCustomerMonthlyIncomeIsNotGreaterThanZero() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerMonthlyIncome(BigDecimal.ZERO);

        //when
        var customerMonthlyIncomeIsValid = customerMonthlyIncomeValidator.isValid(loanRequestDto);

        //then
        assertThat(customerMonthlyIncomeIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoWithCustomerMonthlyIncome(BigDecimal customerMonthlyIncome) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerMonthlyIncome(customerMonthlyIncome)
                .build();
    }
}