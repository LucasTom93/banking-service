package com.asc.loanservice.domain.loan;

import java.util.List;

import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;

public class LoanApplicationServiceResult {
    private final boolean isInputDataValid;
    private final List<String> validationMessages;
    private final LoanRequestRegistrationResultDto loanRequestRegistrationResultDto;

    private LoanApplicationServiceResult(boolean isInputDataValid,
                                         List<String> validationMessages,
                                         LoanRequestRegistrationResultDto loanRequestRegistrationResultDto) {
        this.isInputDataValid = isInputDataValid;
        this.validationMessages = validationMessages;
        this.loanRequestRegistrationResultDto = loanRequestRegistrationResultDto;
    }

    public static LoanApplicationServiceResult of(boolean isInputDataValid, List<String> validationMessages, LoanRequestRegistrationResultDto loanRequestRegistrationResultDto) {
        return new LoanApplicationServiceResult(isInputDataValid, validationMessages, loanRequestRegistrationResultDto);
    }

    public static LoanApplicationServiceResult of(boolean isInputDataValid, List<String> validationMessages) {
        return new LoanApplicationServiceResult(isInputDataValid, validationMessages, null);
    }

    public boolean isInputDataValid() {
        return isInputDataValid;
    }

    public List<String> getValidationMessages() {
        return List.copyOf(validationMessages);
    }

    public LoanRequestRegistrationResultDto getLoanRequestRegistrationResultDto() {
        return loanRequestRegistrationResultDto;
    }
}