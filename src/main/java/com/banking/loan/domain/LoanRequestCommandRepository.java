package com.banking.loan.domain;

import com.banking.shared.domain.annotations.DomainRepository;

@DomainRepository
public interface LoanRequestCommandRepository {

    void save(LoanRequest loanRequest);
}