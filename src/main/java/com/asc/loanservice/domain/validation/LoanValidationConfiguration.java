package com.asc.loanservice.domain.validation;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoanValidationConfiguration {
    private final Set<LoanRequestDataValidator> loanRequestDataValidators;

    LoanValidationConfiguration(Set<LoanRequestDataValidator> loanRequestDataValidators) {
        this.loanRequestDataValidators = loanRequestDataValidators;
    }

    @Bean
    LoanValidationFacade loanValidationFacade() {
        return new LoanValidationFacade(loanRequestDataValidators);
    }
}