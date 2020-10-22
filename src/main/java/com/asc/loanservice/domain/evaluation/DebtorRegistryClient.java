package com.asc.loanservice.domain.evaluation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "debtor-registry", url = "http://localhost:8090")
interface DebtorRegistryClient {

    @GetMapping("/api/customercheck/{customerTaxId}")
    CustomerCheckResultDto check(@PathVariable String customerTaxId);
}