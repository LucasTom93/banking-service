package com.banking.loan.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.banking.loan.domain.evaluation.CustomerCheckResultDto;

@FeignClient(name = "debtor-registry", url = "${debtor.registry.host}")
interface DebtorRegistryFeignClient {

    @GetMapping("/api/customercheck/{customerTaxId}")
    CustomerCheckResultDto check(@PathVariable String customerTaxId);
}