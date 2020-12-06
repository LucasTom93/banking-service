package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class FirstInstallmentDateValidatorTest {

    private final LocalDate currentDate = LocalDate.of(2010, Calendar.FEBRUARY, 1);
    private final FirstInstallmentDateValidator firstInstallmentDateValidator = new FirstInstallmentDateValidator(currentDate);

    @Test
    void shouldPassValidationWhenFirstInstallmentDateIsAfterCurrentDate() {
        //given
        var loanRequestDto = createLoanRequestDtoWithFirstInstallmentDate(currentDate.plusDays(5));

        //when
        var firstInstallmentDateIsValid = firstInstallmentDateValidator.isValid(loanRequestDto);

        //then
        assertThat(firstInstallmentDateIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWhenFirstInstallmentDateIsEmpty() {
        //given
        var loanRequestDto = createLoanRequestDtoWithFirstInstallmentDate(null);

        //when
        var firstInstallmentDateIsValid = firstInstallmentDateValidator.isValid(loanRequestDto);

        //then
        assertThat(firstInstallmentDateIsValid).isFalse();
    }

    @Test
    void shouldNotPassValidationWhenFirstInstallmentDateIsNotAfterCurrentDate() {
        //given
        var loanRequestDto = createLoanRequestDtoWithFirstInstallmentDate(currentDate.minusDays(1));

        //when
        var firstInstallmentDateIsValid = firstInstallmentDateValidator.isValid(loanRequestDto);

        //then
        assertThat(firstInstallmentDateIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoWithFirstInstallmentDate(LocalDate firstInstallmentDate) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withFirstInstallmentDate(firstInstallmentDate)
                .build();
    }
}