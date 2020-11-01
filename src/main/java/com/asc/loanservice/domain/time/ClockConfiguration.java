package com.asc.loanservice.domain.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.asc.loanservice.LoanServiceApplication;

@Configuration
@Profile(LoanServiceApplication.Profile.PRODUCTION)
class ClockConfiguration {

    @Bean
    Clock clock() {
        return new SimpleClock();
    }
}