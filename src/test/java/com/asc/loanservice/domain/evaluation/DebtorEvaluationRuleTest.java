package com.asc.loanservice.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

@ExtendWith(MockitoExtension.class)
class DebtorEvaluationRuleTest {

    @Mock
    private LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker;
    @InjectMocks
    private DebtorEvaluationRule debtorEvaluationRule;

    private final String customerTaxId = UUID.randomUUID().toString();

    @Test
    void shouldApproveLoanRequestWhenReachedAgeThresholdAcceptable() throws Exception {
        //given
        var loanRequestDto = createSimpleLoanRequestDto(customerTaxId);
        var customerCheckResultDto = createCustomerCheckResultDto(customerTaxId, false);
        when(loanDebtorRegistryCircuitBreaker.checkCustomerDebtorRegistry(customerTaxId)).thenReturn(customerCheckResultDto);

        //when
        var evaluationResultDetails = debtorEvaluationRule.evaluate(loanRequestDto);

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.APPROVED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    @Test
    void shouldNotApproveLoanRequestWhenReachedAgeThresholdIsNotAcceptable() throws Exception {
        //given
        var loanRequestDto = createSimpleLoanRequestDto(customerTaxId);
        var customerCheckResultDto = createCustomerCheckResultDto(customerTaxId, true);
        when(loanDebtorRegistryCircuitBreaker.checkCustomerDebtorRegistry(customerTaxId)).thenReturn(customerCheckResultDto);

        //when
        var evaluationResultDetails = debtorEvaluationRule.evaluate(loanRequestDto);

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    @Test
    void shouldNotApproveLoanRequestWhenThereWasThirdPartyServiceConnectionProblem() throws Exception {
        //given
        var loanRequestDto = createSimpleLoanRequestDto(customerTaxId);
        when(loanDebtorRegistryCircuitBreaker.checkCustomerDebtorRegistry(customerTaxId)).thenThrow(new Exception());

        //when
        var evaluationResultDetails = debtorEvaluationRule.evaluate(loanRequestDto);

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    private LoanRequestDto createSimpleLoanRequestDto(String customerTaxId) {
        return LoanRequestDto.Builder.loanRequestDto().withCustomerTaxId(customerTaxId).build();
    }

    private CustomerCheckResultDto createCustomerCheckResultDto(String customerTaxId, Boolean isRegisteredDebtor) {
        return CustomerCheckResultDto.Builder
                .customerCheckResultDto()
                .withCustomerTaxId(customerTaxId)
                .withIsRegisteredDebtor(isRegisteredDebtor)
                .build();
    }
}