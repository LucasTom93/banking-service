package com.banking.loan.application.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

import com.banking.loan.api.contracts.LoanRequestDto;

class CustomerTaxIdValidatorTest {

    private final CustomerTaxIdValidator customerTaxIdValidator = new CustomerTaxIdValidator();

    @Test
    void shouldPassValidationWhenCustomerTaxIdIsProvided() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerTaxId(UUID.randomUUID().toString());

        //when
        var customerTaxIdIsValid = customerTaxIdValidator.isValid(loanRequestDto);

        //then
        assertThat(customerTaxIdIsValid).isTrue();
    }

    @Test
    void shouldNotPassValidationWhenCustomerTaxIdIsNotProvided() {
        //given
        var loanRequestDto = createLoanRequestDtoWithCustomerTaxId(StringUtils.EMPTY);

        //when
        var customerTaxIdIsValid = customerTaxIdValidator.isValid(loanRequestDto);

        //then
        assertThat(customerTaxIdIsValid).isFalse();
    }

    private LoanRequestDto createLoanRequestDtoWithCustomerTaxId(String customerTaxId) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerTaxId(customerTaxId)
                .build();
    }
}