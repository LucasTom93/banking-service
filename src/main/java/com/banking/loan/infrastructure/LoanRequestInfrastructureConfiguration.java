package com.banking.loan.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
class LoanRequestInfrastructureConfiguration {

    @Bean
    LoanRequestCommandRepository loanRequestCommandRepository(LoanRequestCommandSpringDataJpaRepository loanRequestCommandSpringDataJpaRepository) {
        return new LoanRequestCommandJpaRepository(loanRequestCommandSpringDataJpaRepository);
    }

    @Bean
    LoanRequestQueryRepository loanRequestQueryRepository(JdbcOperations jdbcOperations) {
        return new LoanRequestQueryJDBCRepository(jdbcOperations);
    }

    @Bean
    LoanDebtorRegistryCircuitBreaker loanDebtorRegistryCircuitBreaker(DebtorRegistryFeignClient debtorRegistryFeignClient) {
        return new LoanDebtorRegistryResilience4jCircuitBreaker(debtorRegistryFeignClient);
    }
}