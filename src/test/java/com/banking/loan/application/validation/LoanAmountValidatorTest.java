package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class LoanAmountValidatorTest {

    private final LoanAmountValidator loanAmountValidator = new LoanAmountValidator();

    @Test
    void shouldPassValidationWhenLoanAmountIsGreaterThanZero() {
        //given
        var loanRequestDto = createLoanRequestDtoWithLoanAmount(new BigDecimal(1000000));

        //when
        var loanAmountIsIsValid = loanAmountValidator.isValid(loanRequestDto);

        //then
        assertThat(loanAmountIsIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWhenLoanAmountIsEmpty() {
        //given
        var loanRequestDto = createLoanRequestDtoWithLoanAmount(null);

        //when
        var loanAmountIsIsValid = loanAmountValidator.isValid(loanRequestDto);

        //then
        assertThat(loanAmountIsIsValid).isFalse();
    }

    @Test
    void shouldNotPassValidationWhenLoanAmountIsNotGreaterThanZero() {
        //given
        var loanRequestDto = createLoanRequestDtoWithLoanAmount(BigDecimal.ZERO);

        //when
        var loanAmountIsIsValid = loanAmountValidator.isValid(loanRequestDto);

        //then
        assertThat(loanAmountIsIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoWithLoanAmount(BigDecimal loanAmount) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withLoanAmount(loanAmount)
                .build();
    }
}