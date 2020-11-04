package com.asc.loanservice.domain.loan;

import com.asc.loanservice.annotations.ApplicationService;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationPolicyProvider;
import com.asc.loanservice.domain.validation.LoanRequestInputDataValidationService;

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