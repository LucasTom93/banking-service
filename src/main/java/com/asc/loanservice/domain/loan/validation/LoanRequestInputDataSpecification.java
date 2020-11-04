package com.asc.loanservice.domain.loan.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

interface LoanRequestInputDataSpecification {

    boolean isSatisfiedBy(LoanRequestDto loanRequestDto);
}