package com.banking.loan.infrastructure;

import java.util.concurrent.Callable;

import com.banking.loan.domain.evaluation.CustomerCheckResultDto;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

class LoanDebtorRegistryResilience4jCircuitBreaker implements LoanDebtorRegistryCircuitBreaker {
    private final DebtorRegistryFeignClient debtorRegistryFeignClient;
    private final CircuitBreaker circuitBreaker;

    LoanDebtorRegistryResilience4jCircuitBreaker(DebtorRegistryFeignClient debtorRegistryFeignClient) {
        this.debtorRegistryFeignClient = debtorRegistryFeignClient;
        var circuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
        var circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("DebtorRegistryClientCB");
    }

    @Override
    public CustomerCheckResultDto checkCustomerDebtorRegistry(String customerTaxId) throws Exception {
        Callable<CustomerCheckResultDto> checkDebtorCallable = () -> debtorRegistryFeignClient.check(customerTaxId);
        return circuitBreaker.executeCallable(checkDebtorCallable);
    }
}