package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class NumberOfInstallmentsValidatorTest {

    private final NumberOfInstallmentsValidator numberOfInstallmentsValidator = new NumberOfInstallmentsValidator();

    @Test
    void shouldPassValidationWhenNumberOfInstallmentsIsGreaterThanZero() {
        //given
        var loanRequestDto = createLoanRequestDtoNumberOfInstallments(12);

        //when
        var numberOfInstalmentsIsValid = numberOfInstallmentsValidator.isValid(loanRequestDto);

        //then
        assertThat(numberOfInstalmentsIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWhenNumberOfInstallmentsIsEmpty() {
        //given
        var loanRequestDto = createLoanRequestDtoNumberOfInstallments(null);

        //when
        var numberOfInstalmentsIsValid = numberOfInstallmentsValidator.isValid(loanRequestDto);

        //then
        assertThat(numberOfInstalmentsIsValid).isFalse();
    }

    @Test
    void shouldNotPassValidationWhenNumberOfInstallmentsIsNotGreaterThanZero() {
        //given
        var loanRequestDto = createLoanRequestDtoNumberOfInstallments(-1);

        //when
        var numberOfInstalmentsIsValid = numberOfInstallmentsValidator.isValid(loanRequestDto);

        //then
        assertThat(numberOfInstalmentsIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoNumberOfInstallments(Integer numberOfInstallments) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withNumberOfInstallments(numberOfInstallments)
                .build();
    }
}