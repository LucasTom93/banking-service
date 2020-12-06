package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerDateOfBirthValidatorTest {

    private final LocalDate currentDate = LocalDate.of(2010, Calendar.FEBRUARY, 1);
    private final CustomerDateOfBirthValidator customerDateOfBirthValidator = new CustomerDateOfBirthValidator(currentDate);

    @Test
    void shouldPassValidationWhenCustomerDateOfBirthIsBeforeCurrentDate() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerDateOfBirth(currentDate.minusYears(10));

        //when
        var customerDateOfBirthIsValid = customerDateOfBirthValidator.isValid(loanRequestDto);

        //then
        assertThat(customerDateOfBirthIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWhenCustomerDateOfBirthIsEmpty() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerDateOfBirth(null);

        //when
        var customerDateOfBirthIsValid = customerDateOfBirthValidator.isValid(loanRequestDto);

        //then
        assertThat(customerDateOfBirthIsValid).isFalse();
    }

    @Test
    void shouldNotPassValidationWhenCustomerDateOfBirthIsAfterCurrentDate() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerDateOfBirth(currentDate.plusDays(10));

        //when
        var customerDateOfBirthIsValid = customerDateOfBirthValidator.isValid(loanRequestDto);

        //then
        assertThat(customerDateOfBirthIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoWithCustomerDateOfBirth(LocalDate customerDateOfBirth) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerBirthday(customerDateOfBirth)
                .build();
    }
}