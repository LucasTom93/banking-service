package com.asc.loanservice.domain.loan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanRequestBeanConfiguration {

    @Bean
    public LoanRequestQueryRepository loanRequestQueryRepository() {
        return new LoanRequestQueryJDBCRepository();
    }

    public LoanRequestQueryRepository loanRequestQueryInMemoryRepository() {
        return new LoanRequestQueryHashMapRepository();
    }
}