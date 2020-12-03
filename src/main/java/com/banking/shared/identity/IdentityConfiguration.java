package com.banking.shared.identity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class IdentityConfiguration {

    @Bean
    IdentityGenerator identityGenerator() {
        return new IdentityGeneratorImpl();
    }
}