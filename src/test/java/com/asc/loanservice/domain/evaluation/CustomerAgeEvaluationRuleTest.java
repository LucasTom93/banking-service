package com.asc.loanservice.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class CustomerAgeEvaluationRuleTest {

    private CustomerAgeEvaluationRule customerAgeEvaluationRule = new CustomerAgeEvaluationRule();

    @Test
    void shouldApproveLoanRequestWhenReachedAgeThresholdAcceptable() {
        //given
        var loanRequestDto = createLoanRequestDtoForApproval();

        //when
        var evaluationResultDetails = customerAgeEvaluationRule.evaluate(loanRequestDto);

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    @Test
    void shouldNotApproveLoanRequestWhenReachedAgeThresholdIsNotAcceptable() {
        //given
        var loanRequestDto = createLoanRequestDtoForRejection();

        //when
        var evaluationResultDetails = customerAgeEvaluationRule.evaluate(loanRequestDto);

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    private LoanRequestDto createLoanRequestDtoForApproval() {
        var customerBirthday = LocalDate.of(2000, Month.JANUARY, 1);
        var firstInstallmentDate = LocalDate.of(2021, Month.JANUARY, 1);
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerBirthday(customerBirthday)
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withFirstInstallmentDate(firstInstallmentDate)
                .withNumberOfInstallments(13)
                .withCustomerTaxId("1234")
                .withLoanAmount(BigDecimal.valueOf(1000))
                .build();
    }

    private LoanRequestDto createLoanRequestDtoForRejection() {
        var customerBirthday = LocalDate.of(1956, Month.JANUARY, 1);
        var firstInstallmentDate = LocalDate.of(2020, Month.JANUARY, 1);
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerBirthday(customerBirthday)
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withFirstInstallmentDate(firstInstallmentDate)
                .withNumberOfInstallments(14)
                .withCustomerTaxId("1234")
                .withLoanAmount(BigDecimal.valueOf(1000))
                .build();
    }
}