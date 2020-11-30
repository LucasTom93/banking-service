package com.banking.loan.domain.evaluation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.loan.infrastructure.LoanDebtorRegistryCircuitBreaker;
import com.banking.shared.contracts.LoanRequestEvaluationResult;

@ExtendWith(MockitoExtension.class)
class DebtorEvaluationPolicyTest {

    @Mock
    private LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker;
    @InjectMocks
    private DebtorEvaluationPolicy debtorEvaluationRule;

    private final String customerTaxId = UUID.randomUUID().toString();

    @Test
    void shouldApproveLoanRequestWhenReachedAgeThresholdAcceptable() throws Exception {
        //given
        var loanRequestDto = createEvaluationData(customerTaxId);
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
        var loanRequestDto = createEvaluationData(customerTaxId);
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
        var loanRequestDto = createEvaluationData(customerTaxId);
        when(loanDebtorRegistryCircuitBreaker.checkCustomerDebtorRegistry(customerTaxId)).thenThrow(new Exception());

        //when
        var evaluationResultDetails = debtorEvaluationRule.evaluate(loanRequestDto);

        //then
        assertThat(evaluationResultDetails.getLoanRequestEvaluationResult()).isEqualTo(LoanRequestEvaluationResult.REJECTED);
        assertThat(evaluationResultDetails.getEvaluationMessage()).isNotBlank();
    }

    private EvaluationData createEvaluationData(String customerTaxId) {
        return EvaluationData.Builder
                .evaluationData()
                .withCustomerTaxId(customerTaxId)
                .build();
    }

    private CustomerCheckResultDto createCustomerCheckResultDto(String customerTaxId, Boolean isRegisteredDebtor) {
        return CustomerCheckResultDto.Builder
                .customerCheckResultDto()
                .withCustomerTaxId(customerTaxId)
                .withIsRegisteredDebtor(isRegisteredDebtor)
                .build();
    }
}