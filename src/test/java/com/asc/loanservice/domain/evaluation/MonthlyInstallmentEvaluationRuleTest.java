package com.asc.loanservice.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class MonthlyInstallmentEvaluationRuleTest {
    private MonthlyInstallmentEvaluationRule monthlyInstallmentEvaluationRule = new MonthlyInstallmentEvaluationRule();

    @Test
    void shouldApproveLoanRequestWhenReachedAgeThresholdAcceptable() {
        //given
        var loanAmount = BigDecimal.valueOf(40000);
        var numberOfInstallments = 30;
        var monthlyIncome = BigDecimal.valueOf(10000);

        //when
        var evaluationResultDetails = monthlyInstallmentEvaluationRule.evaluate(createLoanRequestDto(loanAmount, numberOfInstallments, monthlyIncome));

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    @Test
    void shouldRejectLoanRequestWhenReachedAgeThresholdIsNotAcceptable() {
        //given
        var loanAmount = BigDecimal.valueOf(12000);
        var numberOfInstallments = 10;
        var monthlyIncome = BigDecimal.valueOf(5000);

        //when
        var evaluationResultDetails = monthlyInstallmentEvaluationRule.evaluate(createLoanRequestDto(loanAmount, numberOfInstallments, monthlyIncome));

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    private LoanRequestDto createLoanRequestDto(BigDecimal loanAmount, int numberOfInstallments, BigDecimal monthlyIncome) {
        var customerBirthday = LocalDate.of(2000, Month.JANUARY, 1);
        var firstInstallmentDate = LocalDate.of(2021, Month.JANUARY, 1);
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerName("John Doe")
                .withCustomerBirthday(customerBirthday)
                .withCustomerMonthlyIncome(monthlyIncome)
                .withFirstInstallmentDate(firstInstallmentDate)
                .withNumberOfInstallments(numberOfInstallments)
                .withCustomerTaxId("1234")
                .withLoanAmount(loanAmount)
                .build();
    }
}