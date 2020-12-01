package com.banking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.banking.shared.time.Clock;

@Configuration
@Profile(BankingServiceApplication.Profiles.TEST)
class LoanTestBeansConfiguration {

    @Bean
    Clock clock() {
        return new Clock() {
            @Override
            public LocalDate getCurrentDate() {
                return LocalDate.of(2020, 1, 1);
            }

            @Override
            public LocalDateTime getCurrentLocalDateTime() {
                return LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            }
        };
    }
}