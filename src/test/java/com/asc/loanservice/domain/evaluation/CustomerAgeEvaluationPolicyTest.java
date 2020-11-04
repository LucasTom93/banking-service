package com.asc.loanservice.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class CustomerAgeEvaluationPolicyTest {

    private CustomerAgeEvaluationPolicy customerAgeEvaluationRule = new CustomerAgeEvaluationPolicy();

    @Test
    void shouldApproveLoanRequestWhenReachedAgeThresholdAcceptable() {
        //given
        var customerBirthday = LocalDate.of(2000, Month.JANUARY, 1);
        var firstInstallmentDate = LocalDate.of(2021, Month.JANUARY, 1);

        //when
        var evaluationResultDetails = customerAgeEvaluationRule.evaluate(createEvaluationData(customerBirthday, firstInstallmentDate));

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    @Test
    void shouldNotApproveLoanRequestWhenReachedAgeThresholdIsNotAcceptable() {
        //given
        var customerBirthday = LocalDate.of(1956, Month.JANUARY, 1);
        var firstInstallmentDate = LocalDate.of(2020, Month.JANUARY, 1);

        //when
        var evaluationResultDetails = customerAgeEvaluationRule.evaluate(createEvaluationData(customerBirthday, firstInstallmentDate));

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    private EvaluationData createEvaluationData(LocalDate customerDateOfBirth, LocalDate firstInstallmentDate) {
        return EvaluationData.Builder
                .evaluationData()
                .withCustomerDateOfBirth(customerDateOfBirth)
                .withFirstInstallmentDate(firstInstallmentDate)
                .withNumberOfInstallments(13)
                .build();
    }
}