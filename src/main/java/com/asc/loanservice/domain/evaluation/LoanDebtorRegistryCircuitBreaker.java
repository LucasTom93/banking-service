package com.asc.loanservice.domain.evaluation;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Component
class LoanDebtorRegistryCircuitBreaker {
    private final DebtorRegistryClient debtorRegistryClient;
    private final CircuitBreakerConfig circuitBreakerConfig;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final CircuitBreaker circuitBreaker;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    LoanDebtorRegistryCircuitBreaker(DebtorRegistryClient debtorRegistryClient) {
        this.debtorRegistryClient = debtorRegistryClient;
        this.circuitBreakerConfig = CircuitBreakerConfig.ofDefaults();
        this.circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("DebtorRegistryClientCB");
    }

    CustomerCheckResultDto checkCustomerDebtorRegistry(String customerTaxId) throws Exception {
        Callable<CustomerCheckResultDto> checkDebtorCallable = () -> debtorRegistryClient.check(customerTaxId);
        return circuitBreaker.executeCallable(checkDebtorCallable);
    }
}