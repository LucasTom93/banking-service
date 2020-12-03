package com.banking.loan.infrastructure.circuitbreaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CircuitBreakerConfiguration {

    @Bean
    LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker(DebtorRegistryFeignClient debtorRegistryFeignClient) {
        return new LoanDebtorRegistryResilience4jCircuitBreaker(debtorRegistryFeignClient);
    }
}