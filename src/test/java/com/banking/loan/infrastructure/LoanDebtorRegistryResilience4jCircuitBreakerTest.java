package com.banking.loan.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.loan.domain.evaluation.CustomerCheckResultDto;

@ExtendWith(MockitoExtension.class)
class LoanDebtorRegistryResilience4jCircuitBreakerTest {

    @Mock
    private DebtorRegistryFeignClient debtorRegistryFeignClient;
    @InjectMocks
    private LoanDebtorRegistryResilience4jCircuitBreaker loanDebtorRegistryResilience4jCircuitBreaker;

    @Test
    void shouldCheckCustomerInDebtorRegistry() throws Exception {
        //given
        var customerTaxId = UUID.randomUUID().toString();
        var customerCheckResultFromFeignClient = mock(CustomerCheckResultDto.class);
        when(debtorRegistryFeignClient.check(customerTaxId)).thenReturn(customerCheckResultFromFeignClient);

        //when
        var customerCheckCircuitBreakerResult = loanDebtorRegistryResilience4jCircuitBreaker.checkCustomerDebtorRegistry(customerTaxId);

        //then
        assertThat(customerCheckCircuitBreakerResult).isEqualTo(customerCheckResultFromFeignClient);
    }
}