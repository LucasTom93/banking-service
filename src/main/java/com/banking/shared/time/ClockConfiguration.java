package com.banking.shared.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.banking.BankingServiceApplication;

@Configuration
@Profile(BankingServiceApplication.Profiles.PRODUCTION)
class ClockConfiguration {

    @Bean
    Clock clock() {
        return new SimpleClock();
    }
}