package com.banking.loan.infrastructure;

import com.banking.loan.domain.LoanRequest;
import com.banking.shared.domain.annotations.DomainRepository;

@DomainRepository
public interface LoanRequestCommandRepository {

    void save(LoanRequest loanRequest);
}