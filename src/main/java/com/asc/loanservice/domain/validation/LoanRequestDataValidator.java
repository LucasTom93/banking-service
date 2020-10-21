package com.asc.loanservice.domain.validation;

import com.asc.loanservice.contracts.LoanRequestDto;

public interface LoanRequestDataValidator {

    LoanRequestValidationResult validate(LoanRequestDto loanRequestDto);
}