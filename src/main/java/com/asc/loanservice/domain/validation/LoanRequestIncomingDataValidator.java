package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

interface LoanRequestIncomingDataValidator {

    boolean isValid(LoanRequestDto loanRequestDto);
}