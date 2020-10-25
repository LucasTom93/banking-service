package com.asc.loanservice;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:feign/feign_clients.properties")
class FeignClientsConfigurationProperties {
}