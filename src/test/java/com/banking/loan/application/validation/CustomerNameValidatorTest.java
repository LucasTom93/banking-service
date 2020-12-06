package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerNameValidatorTest {

    private final CustomerNameValidator customerNameValidator = new CustomerNameValidator();

    @Test
    void shouldPassValidationWHenCustomerNameIsProvided() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerName("Bob");

        //when
        boolean customerNameIsValid = customerNameValidator.isValid(loanRequestDto);

        //then
        assertThat(customerNameIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWHenCustomerNameIsNotProvided() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerName(null);

        //when
        boolean customerNameIsValid = customerNameValidator.isValid(loanRequestDto);

        //then
        assertThat(customerNameIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoWithCustomerName(String customerName) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName(customerName)
                .build();
    }
}