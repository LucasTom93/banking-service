package com.asc.loanservice.domain.loan;

import java.util.Optional;

import com.asc.loanservice.contracts.LoanRequestDataDto;

public interface LoanRequestQueryRepository {

    Optional<LoanRequestDataDto> findByLoanNumber(String loanNumber);
}
