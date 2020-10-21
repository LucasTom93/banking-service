package com.asc.loanservice.domain.loan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanConfiguration {

    @Bean
    public LoanRequestQueryRepository loanRequestQueryRepository() {
        return new LoanRequestQueryJDBCRepository();
    }

    public LoanRequestQueryRepository loanRequestQueryHashMapRepository() {
        return new LoanRequestQueryHashMapRepository();
    }
}