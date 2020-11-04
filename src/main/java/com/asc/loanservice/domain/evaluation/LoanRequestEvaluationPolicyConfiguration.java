package com.asc.loanservice.domain.evaluation;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoanRequestEvaluationPolicyConfiguration {

    @Bean
    LoanRequestEvaluationPolicyProvider loanRequestEvaluationPolicyProvider(LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker) {
        return new LoanRequestEvaluationPolicyProvider(Set.of(
                new CustomerAgeEvaluationPolicy(),
                new MonthlyInstallmentEvaluationPolicy(),
                new DebtorEvaluationPolicy(loanDebtorRegistryCircuitBreaker)
        ));
    }
}