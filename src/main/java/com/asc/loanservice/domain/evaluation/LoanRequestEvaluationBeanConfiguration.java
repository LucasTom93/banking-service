package com.asc.loanservice.domain.evaluation;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoanRequestEvaluationBeanConfiguration {
    private final Set<LoanRequestEvaluationRule> loanRequestEvaluationRules;

    LoanRequestEvaluationBeanConfiguration(Set<LoanRequestEvaluationRule> loanRequestEvaluationRules) {
        this.loanRequestEvaluationRules = loanRequestEvaluationRules;
    }

    @Bean
    LoanRequestEvaluationFacade loanRequestEvaluationFacade() {
        return new LoanRequestEvaluationFacade(loanRequestEvaluationRules);
    }
}