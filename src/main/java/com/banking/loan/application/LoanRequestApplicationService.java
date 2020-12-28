package com.banking.loan.application;

import com.banking.loan.api.contracts.LoanRequestDto;
import com.banking.loan.api.contracts.LoanRequestRegistrationResultDto;
import com.banking.loan.application.validation.LoanRequestInputDataValidationService;
import com.banking.loan.application.validation.LoanValidationException;
import com.banking.loan.domain.evaluation.LoanRequestEvaluationPolicyProvider;
import com.banking.loan.domain.request.LoanRequestCommandRepository;
import com.banking.loan.domain.request.LoanRequestFactory;
import com.banking.shared.domain.annotations.ApplicationService;

@ApplicationService
public class LoanRequestApplicationService {
    private final LoanRequestInputDataValidationService loanRequestInputDataValidationService;
    private final LoanRequestFactory loanRequestFactory;
    private final LoanRequestCommandRepository loanRequestCommandRepository;
    private final LoanRequestEvaluationPolicyProvider loanRequestEvaluationPolicyProvider;

    public LoanRequestApplicationService(LoanRequestInputDataValidationService loanRequestInputDataValidationService,
                                         LoanRequestFactory loanRequestFactory,
                                         LoanRequestCommandRepository loanRequestCommandRepository,
                                         LoanRequestEvaluationPolicyProvider loanRequestEvaluationPolicyProvider) {
        this.loanRequestInputDataValidationService = loanRequestInputDataValidationService;
        this.loanRequestFactory = loanRequestFactory;
        this.loanRequestCommandRepository = loanRequestCommandRepository;
        this.loanRequestEvaluationPolicyProvider = loanRequestEvaluationPolicyProvider;
    }

    public LoanRequestRegistrationResultDto registerLoanRequest(LoanRequestDto loanRequestDto) throws LoanValidationException {
        var isLoanRequestDtoValid = loanRequestInputDataValidationService.isValid(loanRequestDto);
        if (!isLoanRequestDtoValid) {
            throw new LoanValidationException("Input data invalid");
        }
        var loanRequest = loanRequestFactory.createLoanRequest(loanRequestDto);
        loanRequest.evaluate(loanRequestEvaluationPolicyProvider.getLoanRequestEvaluationPolicies());
        loanRequestCommandRepository.save(loanRequest);
        return LoanRequestRegistrationResultDto.of(loanRequest.getLoanRequestNumber(), loanRequest.getLoanRequestEvaluationResult());
    }
}