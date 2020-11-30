package com.banking.loan.infrastructure;

import java.util.Optional;

import com.banking.shared.contracts.LoanRequestDataDto;
import com.banking.shared.domain.annotations.DomainRepository;

@DomainRepository
public interface LoanRequestQueryRepository {

    Optional<LoanRequestDataDto> findByLoanNumber(String loanNumber);
}