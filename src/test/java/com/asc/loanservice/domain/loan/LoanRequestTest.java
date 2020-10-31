package com.asc.loanservice.domain.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationRule;

@ExtendWith(MockitoExtension.class)
class LoanRequestTest {

    private LoanRequest loanRequest = new LoanRequest();

    @Test
    void shouldEvaluateForApproval() {
        //given
        var loanRequestDto = mock(LoanRequestDto.class);
        var loanRequestEvaluationRule = mock(LoanRequestEvaluationRule.class);
        var requestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.APPROVED, "Evaluation passed");
        when(loanRequestEvaluationRule.evaluate(loanRequestDto)).thenReturn(requestEvaluationResultDetails);

        //when
        loanRequest.evaluate(loanRequestDto, Set.of(loanRequestEvaluationRule));

        //then
        assertThat(loanRequest.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
    }

    @Test
    void shouldEvaluateForRejection() {
        //given
        var loanRequestDto = mock(LoanRequestDto.class);
        var loanRequestEvaluationRule = mock(LoanRequestEvaluationRule.class);
        var requestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.REJECTED, "Evaluation did not pass");
        when(loanRequestEvaluationRule.evaluate(loanRequestDto)).thenReturn(requestEvaluationResultDetails);

        //when
        loanRequest.evaluate(loanRequestDto, Set.of(loanRequestEvaluationRule));

        //then
        assertThat(loanRequest.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
    }
}