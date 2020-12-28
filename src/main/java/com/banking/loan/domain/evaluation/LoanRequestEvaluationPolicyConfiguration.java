package com.banking.loan.domain.evaluation;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.banking.loan.domain.request.LoanRequestValueObjectFactory;
import com.banking.loan.infrastructure.circuitbreaker.LoanDebtorRegistryCircuitBreaker;

@Configuration
class LoanRequestEvaluationPolicyConfiguration {

    @Bean
    LoanRequestEvaluationPolicyProvider loanRequestEvaluationPolicyProvider(LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker, LoanRequestValueObjectFactory loanRequestValueObjectFactory) {
        return new LoanRequestEvaluationPolicyProvider(Set.of(
                new CustomerAgeEvaluationPolicy(loanRequestValueObjectFactory),
                new MonthlyInstallmentEvaluationPolicy(loanRequestValueObjectFactory),
                new DebtorEvaluationPolicy(loanDebtorRegistryCircuitBreaker)
        ));
    }
}