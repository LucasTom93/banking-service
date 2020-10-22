package com.asc.loanservice.domain.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asc.loanservice.contracts.LoanRequestDto;

public class LoanValidationFacade {
    private final Set<LoanRequestDataValidator> loanRequestDataValidators;

    public LoanValidationFacade(Set<LoanRequestDataValidator> loanRequestDataValidators) {
        this.loanRequestDataValidators = loanRequestDataValidators;
    }

    public List<LoanRequestValidationResult> validateLoanRequest(LoanRequestDto loanRequestDto) {
        return loanRequestDataValidators
                .stream()
                .map(validator -> validator.validate(loanRequestDto))
                .collect(Collectors.toList());
    }
}