package com.banking.end2end;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.banking.loan.api.contracts.LoanRequestDto;
import com.banking.loan.api.contracts.LoanRequestEvaluationResult;
import com.banking.loan.application.LoanRequestApplicationService;
import com.banking.loan.application.validation.LoanValidationException;

@EndToEndTest
class LoanRequestApplicationServiceTest {

    @Autowired
    private LoanRequestApplicationService loanRequestApplicationService;

    @Test
    void shouldRegisterLoanRequestWhenInputDataIsValid() throws LoanValidationException {
        //given
        var loanRequestDto = createCorrectLoanRequestDto();

        //when
        var loanRequestRegistrationResultDto = loanRequestApplicationService.registerLoanRequest(loanRequestDto);

        //then
        assertThat(loanRequestRegistrationResultDto.getEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(loanRequestRegistrationResultDto.getLoanRequestNumber()).isNotEmpty();
    }

    @Test
    void shouldNotRegisterLoanRequestWhenInputDataIsNotValid() {
        //given
        var loanRequestDto = createIncorrectLoanRequestDto();

        //when //then
        assertThatThrownBy(() -> loanRequestApplicationService.registerLoanRequest(loanRequestDto)).isInstanceOf(LoanValidationException.class);
    }

    private LoanRequestDto createCorrectLoanRequestDto() {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerTaxId(UUID.randomUUID().toString())
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withLoanAmount(BigDecimal.valueOf(40000))
                .withNumberOfInstallments(30)
                .withFirstInstallmentDate(LocalDate.of(2021, 1, 1))
                .withCustomerBirthday(LocalDate.of(2000, 12, 31))
                .build();
    }

    private LoanRequestDto createIncorrectLoanRequestDto() {
        return LoanRequestDto.Builder.loanRequestDto().build();
    }
}