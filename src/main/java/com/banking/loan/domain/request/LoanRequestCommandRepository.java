package com.banking.loan.domain.request;

import com.banking.shared.domain.annotations.DomainRepository;

@DomainRepository
public interface LoanRequestCommandRepository {

    void save(LoanRequest loanRequest);
}