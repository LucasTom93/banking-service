package com.banking.loan.infrastructure.circuitbreaker;

public interface LoanDebtorRegistryCircuitBreaker {

    CustomerCheckResultDto checkCustomerDebtorRegistry(String customerTaxId) throws Exception;
}