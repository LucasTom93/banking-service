package com.banking.loan.infrastructure.circuitbreaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        var isRegisteredDebtor = false;
        var customerCheckResultFromFeignClient = CustomerCheckResultDto.Builder
                .customerCheckResultDto()
                .withCustomerTaxId(customerTaxId)
                .withIsRegisteredDebtor(isRegisteredDebtor)
                .build();
        when(debtorRegistryFeignClient.check(customerTaxId)).thenReturn(customerCheckResultFromFeignClient);

        //when
        var customerCheckCircuitBreakerResult = loanDebtorRegistryResilience4jCircuitBreaker.checkCustomerDebtorRegistry(customerTaxId);

        //then
        assertThat(customerCheckCircuitBreakerResult).extracting(
                "customerTaxId",
                "registeredDebtor"
        ).containsExactly(
                customerTaxId,
                isRegisteredDebtor
        );
    }
}