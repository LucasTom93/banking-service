package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

interface LoanRequestInputDataSpecification {

    boolean isSatisfiedBy(LoanRequestDto loanRequestDto);
}