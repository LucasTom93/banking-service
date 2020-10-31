package com.asc.loanservice.domain.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;

@ExtendWith(MockitoExtension.class)
class LoanRequestApplicationServiceTest {
    @Mock
    private LoanRequestCommandRepository loanRequestCommandRepository;
    @Mock
    private LoanRequestFactory loanRequestFactory;
    @InjectMocks
    private LoanRequestApplicationService loanRequestApplicationService;

    @Test
    void shouldRegisterLoanRequestWhenInputDataIsValid() throws LoanValidationException {
        //given
        var loanRequestDto = mock(LoanRequestDto.class);
        var loanRequest = mock(LoanRequest.class);
        when(loanRequestFactory.createEvaluatedLoanRequest(loanRequestDto)).thenReturn(loanRequest);

        //when
        var loanRequestRegistrationResultDto = loanRequestApplicationService.registerLoanRequest(loanRequestDto);

        //then
        verifyNoMoreInteractions(loanRequestFactory);
        verify(loanRequestCommandRepository).save(loanRequest);
        assertThat(loanRequestRegistrationResultDto).isNotNull();
    }

    @Test
    void shouldNotRegisterLoanRequestWhenInputDataIsNotValid() throws LoanValidationException {
        //given
        var loanRequestDto = mock(LoanRequestDto.class);
        var validationMessage = "Invalid input data";
        when(loanRequestFactory.createEvaluatedLoanRequest(loanRequestDto)).thenThrow(new LoanValidationException(validationMessage));

        //when //then
        assertThatThrownBy(() -> loanRequestApplicationService.registerLoanRequest(loanRequestDto), validationMessage).isInstanceOf(LoanValidationException.class);
        verifyZeroInteractions(loanRequestCommandRepository);
        verifyNoMoreInteractions(loanRequestFactory);
    }
}