package com.banking.loan.infrastructure;

import com.banking.loan.domain.evaluation.CustomerCheckResultDto;

public interface LoanDebtorRegistryCircuitBreaker {

    CustomerCheckResultDto checkCustomerDebtorRegistry(String customerTaxId) throws Exception;
}