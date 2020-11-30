package com.banking.loan.application.validation;

import com.banking.shared.contracts.LoanRequestDto;

interface LoanRequestIncomingDataValidator {

    boolean isValid(LoanRequestDto loanRequestDto);
}