package com.banking.loan.domain.evaluation;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Component
class LoanDebtorRegistryCircuitBreaker {
    private final DebtorRegistryFeignClient debtorRegistryFeignClient;
    private final CircuitBreaker circuitBreaker;

    LoanDebtorRegistryCircuitBreaker(DebtorRegistryFeignClient debtorRegistryFeignClient) {
        this.debtorRegistryFeignClient = debtorRegistryFeignClient;
        var circuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
        var circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("DebtorRegistryClientCB");
    }

    CustomerCheckResultDto checkCustomerDebtorRegistry(String customerTaxId) throws Exception {
        Callable<CustomerCheckResultDto> checkDebtorCallable = () -> debtorRegistryFeignClient.check(customerTaxId);
        return circuitBreaker.executeCallable(checkDebtorCallable);
    }
}