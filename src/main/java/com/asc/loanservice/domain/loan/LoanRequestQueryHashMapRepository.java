package com.asc.loanservice.domain.loan;

import java.util.Optional;

import com.asc.loanservice.contracts.LoanRequestDataDto;

class LoanRequestQueryHashMapRepository implements LoanRequestQueryRepository {

    @Override
    public Optional<LoanRequestDataDto> findByLoanNumber(String loanRequestNumber) {
        return Optional.empty();
    }
}