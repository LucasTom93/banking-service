package com.asc.loanservice.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;

@ExtendWith(MockitoExtension.class)
class LoanRequestEvaluationFacadeTest {
    private LoanRequestEvaluationRule loanRequestEvaluationRule;
    private LoanRequestEvaluationFacade loanRequestEvaluationFacade;

    @BeforeEach
    void setUp() {
        loanRequestEvaluationRule = mock(LoanRequestEvaluationRule.class);
        loanRequestEvaluationFacade = new LoanRequestEvaluationFacade(Set.of(loanRequestEvaluationRule));
    }

    @Test
    void shouldReturnEvaluationDetails() {
        //given
        var loanRequestDto = mock(LoanRequestDto.class);
        var loanRequestEvaluationResultDetails = mock(LoanRequestEvaluationResultDetails.class);
        when(loanRequestEvaluationRule.evaluate(loanRequestDto)).thenReturn(loanRequestEvaluationResultDetails);

        //when
        var resultDetailsSet = loanRequestEvaluationFacade.evaluate(loanRequestDto);

        //then
        assertThat(resultDetailsSet).containsExactly(loanRequestEvaluationResultDetails);
    }
}