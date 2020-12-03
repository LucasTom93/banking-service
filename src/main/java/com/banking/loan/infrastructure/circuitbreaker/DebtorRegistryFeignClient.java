package com.banking.loan.infrastructure.circuitbreaker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "debtor-registry", url = "${debtor.registry.host}")
interface DebtorRegistryFeignClient {

    @GetMapping("/api/customercheck/{customerTaxId}")
    CustomerCheckResultDto check(@PathVariable String customerTaxId);
}