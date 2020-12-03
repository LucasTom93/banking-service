package com.banking.loan.infrastructure.circuitbreaker;

import java.util.concurrent.Callable;

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