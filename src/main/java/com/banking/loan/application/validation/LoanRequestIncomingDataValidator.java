package com.banking.loan.application.validation;

import com.banking.loan.api.contracts.LoanRequestDto;

interface LoanRequestIncomingDataValidator {

    boolean isValid(LoanRequestDto loanRequestDto);
}