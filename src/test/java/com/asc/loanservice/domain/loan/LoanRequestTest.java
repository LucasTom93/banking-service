package com.asc.loanservice.domain.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.evaluation.EvaluationData;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationRule;

@ExtendWith(MockitoExtension.class)
class LoanRequestTest {

    private LoanRequest loanRequest;

    @BeforeEach
    void init() throws LoanValidationException {
        var currentDate = LocalDate.of(2020, 1, 1);
        loanRequest = LoanRequest.Builder
                .loanRequest()
                .withCustomerDateOfBirth(CustomerDateOfBirth.of(LocalDate.of(2000, 1, 1), currentDate))
                .withCustomerMonthlyIncome(CustomerMonthlyIncome.of(BigDecimal.valueOf(30000)))
                .withFirstInstallmentDate(FirstInstallmentDate.of(LocalDate.of(2021, 1, 1), currentDate))
                .withNumberOfInstallments(NumberOfInstallments.of(10))
                .withCustomerTaxId(CustomerTaxId.of(UUID.randomUUID().toString()))
                .withLoanAmount(LoanAmount.of(BigDecimal.valueOf(40000)))
                .build();
    }

    @Test
    void shouldEvaluateForApproval() {
        //given
        var loanRequestEvaluationRule = mock(LoanRequestEvaluationRule.class);
        var requestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.APPROVED, "Evaluation passed");
        when(loanRequestEvaluationRule.evaluate(any(EvaluationData.class))).thenReturn(requestEvaluationResultDetails);

        //when
        loanRequest.evaluate(Set.of(loanRequestEvaluationRule));

        //then
        assertThat(loanRequest.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
    }

    @Test
    void shouldEvaluateForRejection() {
        //given
        var loanRequestEvaluationRule = mock(LoanRequestEvaluationRule.class);
        var requestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.REJECTED, "Evaluation did not pass");
        when(loanRequestEvaluationRule.evaluate(any(EvaluationData.class))).thenReturn(requestEvaluationResultDetails);

        //when
        loanRequest.evaluate(Set.of(loanRequestEvaluationRule));

        //then
        assertThat(loanRequest.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
    }
}