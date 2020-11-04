package com.asc.loanservice.domain.loan.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.domain.time.Clock;

@ExtendWith(MockitoExtension.class)
class LoanRequestInputDataValidationServiceTest {

    @Mock
    private Clock clock;
    @InjectMocks
    private LoanRequestInputDataValidationService loanRequestInputDataValidationService;

    @Test
    void shouldPerformValidation() {
        var loanRequestDto = mock(LoanRequestDto.class);

        var validationResult = loanRequestInputDataValidationService.isValid(loanRequestDto);

        assertThat(validationResult).isNotNull();
        verify(clock).getCurrentDate();
    }
}