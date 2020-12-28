package com.banking.loan.domain.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.loan.api.contracts.LoanRequestEvaluationResult;
import com.banking.loan.domain.evaluation.EvaluationData;
import com.banking.loan.domain.evaluation.LoanRequestEvaluationPolicy;
import com.banking.loan.domain.evaluation.LoanRequestEvaluationResultDetails;

@ExtendWith(MockitoExtension.class)
class LoanRequestTest {

    private LoanRequest loanRequest;

    @BeforeEach
    void init() {
        loanRequest = LoanRequest.Builder
                .loanRequest()
                .withCustomerDateOfBirth(LocalDate.of(2000, Month.JANUARY, 1))
                .withCustomerMonthlyIncome(BigDecimal.valueOf(30000))
                .withFirstInstallmentDate(LocalDate.of(2021, Month.JANUARY, 1))
                .withNumberOfInstallments(10)
                .withCustomerTaxId(UUID.randomUUID().toString())
                .withLoanAmount(BigDecimal.valueOf(40000))
                .build();
    }

    @Test
    void shouldEvaluateForApproval() {
        //given
        var loanRequestEvaluationPolicy = mock(LoanRequestEvaluationPolicy.class);
        var requestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.APPROVED, "Evaluation passed");
        when(loanRequestEvaluationPolicy.evaluate(any(EvaluationData.class))).thenReturn(requestEvaluationResultDetails);

        //when
        loanRequest.evaluate(Set.of(loanRequestEvaluationPolicy));

        //then
        assertThat(loanRequest.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
    }

    @Test
    void shouldEvaluateForRejection() {
        //given
        var loanRequestEvaluationPolicy = mock(LoanRequestEvaluationPolicy.class);
        var requestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.REJECTED, "Evaluation did not pass");
        when(loanRequestEvaluationPolicy.evaluate(any(EvaluationData.class))).thenReturn(requestEvaluationResultDetails);

        //when
        loanRequest.evaluate(Set.of(loanRequestEvaluationPolicy));

        //then
        assertThat(loanRequest.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
    }
}