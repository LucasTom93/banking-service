package com.asc.loanservice.domain.loan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class LoanRequestBeanConfiguration {
    private final JdbcOperations jdbcOperations;

    public LoanRequestBeanConfiguration(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Bean
    public LoanRequestQueryRepository loanRequestQueryRepository() {
        return new LoanRequestQueryJDBCRepository(jdbcOperations);
    }
}