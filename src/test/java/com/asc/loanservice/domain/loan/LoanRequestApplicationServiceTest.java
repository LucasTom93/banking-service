package com.asc.loanservice.domain.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationFacade;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.asc.loanservice.domain.time.Clock;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;
import com.asc.loanservice.domain.validation.LoanValidationFacade;

@ExtendWith(MockitoExtension.class)
class LoanRequestApplicationServiceTest {
    @Mock
    private LoanRequestCommandRepository loanRequestCommandRepository;
    @Mock
    private LoanValidationFacade loanValidationFacade;
    @Mock
    private Clock clock;
    @Mock
    private LoanRequestEvaluationFacade loanRequestEvaluationFacade;
    @InjectMocks
    private LoanRequestApplicationService loanRequestApplicationService;

    @Test
    void shouldRegisterLoanRequestWhenInputDataIsValid() {
        //given
        var customerTaxId = UUID.randomUUID().toString();
        var loanRequestDto = createCorrectLoanRequestDto(customerTaxId);
        var loanRequestValidationResults = List.of(LoanRequestValidationResult.valid());
        var evaluationMessage = "Age is correct";
        var loanRequestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.APPROVED, evaluationMessage);
        when(loanValidationFacade.validateLoanRequest(loanRequestDto)).thenReturn(loanRequestValidationResults);
        when(clock.getCurrentDate()).thenReturn(LocalDate.now());
        when(loanRequestEvaluationFacade.evaluate(loanRequestDto)).thenReturn(Set.of(loanRequestEvaluationResultDetails));

        //when
        var loanApplicationServiceResult = loanRequestApplicationService.registerLoanRequest(loanRequestDto);

        //then
        assertThat(loanApplicationServiceResult.isInputDataValid()).isTrue();
        assertThat(loanApplicationServiceResult.getValidationMessages()).isEmpty();
        assertThat(loanApplicationServiceResult.getLoanRequestRegistrationResultDto().getLoanRequestNumber()).isNotBlank();
        assertThat(loanApplicationServiceResult.getLoanRequestRegistrationResultDto().getEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);

        verify(loanRequestCommandRepository).save(any(LoanRequest.class));
    }

    @Test
    void shouldNotRegisterLoanRequestWhenInputDataIsNotValid() {
        //given
        var customerTaxId = UUID.randomUUID().toString();
        var loanRequestDto = createIncorrectLoanRequestDto(customerTaxId);
        var validationMessage = "Data not valid";
        var loanRequestValidationResults = List.of(LoanRequestValidationResult.invalid(validationMessage));
        when(loanValidationFacade.validateLoanRequest(loanRequestDto)).thenReturn(loanRequestValidationResults);

        //when
        var loanApplicationServiceResult = loanRequestApplicationService.registerLoanRequest(loanRequestDto);

        //then
        assertThat(loanApplicationServiceResult.isInputDataValid()).isFalse();
        assertThat(loanApplicationServiceResult.getValidationMessages()).containsExactly(validationMessage);
        assertThat(loanApplicationServiceResult.getLoanRequestRegistrationResultDto()).isNull();
        verifyZeroInteractions(loanRequestCommandRepository);
    }

    private LoanRequestDto createIncorrectLoanRequestDto(String customerTaxId) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerTaxId(customerTaxId)
                .build();
    }

    private LoanRequestDto createCorrectLoanRequestDto(String customerTaxId) {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerBirthday(LocalDate.now())
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withFirstInstallmentDate(LocalDate.now())
                .withNumberOfInstallments(10)
                .withCustomerTaxId(customerTaxId)
                .withLoanAmount(BigDecimal.valueOf(1000))
                .build();
    }
}