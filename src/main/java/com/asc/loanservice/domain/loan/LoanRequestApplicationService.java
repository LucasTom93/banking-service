package com.asc.loanservice.domain.loan;

import com.asc.loanservice.annotations.ApplicationService;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;

@ApplicationService
public class LoanRequestApplicationService {
    private final LoanRequestFactory loanRequestFactory;
    private final LoanRequestCommandRepository loanRequestCommandRepository;

    public LoanRequestApplicationService(LoanRequestFactory loanRequestFactory,
                                         LoanRequestCommandRepository loanRequestCommandRepository) {
        this.loanRequestFactory = loanRequestFactory;
        this.loanRequestCommandRepository = loanRequestCommandRepository;
    }

    public LoanRequestRegistrationResultDto registerLoanRequest(LoanRequestDto loanRequestDto) throws LoanValidationException {
        var loanRequest = loanRequestFactory.createEvaluatedLoanRequest(loanRequestDto);
        loanRequestCommandRepository.save(loanRequest);
        return LoanRequestRegistrationResultDto.of(loanRequest.getLoanRequestNumber(), loanRequest.getLoanRequestEvaluationResult());
    }
}