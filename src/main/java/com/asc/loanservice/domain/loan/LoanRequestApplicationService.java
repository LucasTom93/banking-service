package com.asc.loanservice.domain.loan;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.asc.loanservice.annotations.ApplicationService;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationFacade;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.asc.loanservice.domain.time.Clock;
import com.asc.loanservice.domain.validation.LoanRequestValidationResult;
import com.asc.loanservice.domain.validation.LoanValidationFacade;

@ApplicationService
public class LoanRequestApplicationService {
    private final LoanValidationFacade loanValidationFacade;
    private final LoanRequestCommandRepository loanRequestCommandRepository;
    private final Clock clock;
    private final LoanRequestEvaluationFacade loanRequestEvaluationFacade;

    public LoanRequestApplicationService(LoanValidationFacade loanValidationFacade,
                                         LoanRequestCommandRepository loanRequestCommandRepository,
                                         Clock clock,
                                         LoanRequestEvaluationFacade loanRequestEvaluationFacade) {
        this.loanValidationFacade = loanValidationFacade;
        this.loanRequestCommandRepository = loanRequestCommandRepository;
        this.clock = clock;
        this.loanRequestEvaluationFacade = loanRequestEvaluationFacade;
    }

    public LoanApplicationServiceResult registerLoanRequest(LoanRequestDto loanRequestDto) {
        var loanRequestValidationResults = loanValidationFacade.validateLoanRequest(loanRequestDto);
        var isRequestDtoValid = checkRequestDtoIsValid(loanRequestValidationResults);
        if (!isRequestDtoValid) {
            var validationMessages = getValidationMessages(loanRequestValidationResults);
            return LoanApplicationServiceResult.of(false, validationMessages);
        }

        var loanRequestEvaluationDetailsSet = loanRequestEvaluationFacade.evaluate(loanRequestDto);
        var areAllEvaluationRulesApproved = checkAllEvaluationRulesAreApproved(loanRequestEvaluationDetailsSet);
        var loanRequestEvaluationResult = areAllEvaluationRulesApproved ? LoanRequestEvaluationResult.APPROVED : LoanRequestEvaluationResult.REJECTED;
        var loanRequest = LoanRequestFactory.createLoanRequest(loanRequestDto, loanRequestEvaluationResult, clock.getCurrentLocalDateTime());

        loanRequestCommandRepository.save(loanRequest);

        var loanRequestRegistrationResultDto = LoanRequestRegistrationResultDto.of(loanRequest.getLoanRequestNumber(), loanRequestEvaluationResult);
        return LoanApplicationServiceResult.of(true, Collections.emptyList(), loanRequestRegistrationResultDto);
    }

    private boolean checkAllEvaluationRulesAreApproved(Set<LoanRequestEvaluationResultDetails> loanRequestEvaluationDetailsSet) {
        return loanRequestEvaluationDetailsSet
                .stream()
                .map(LoanRequestEvaluationResultDetails::getLoanRequestEvaluationResult)
                .allMatch(evaluationResult -> evaluationResult.equals(LoanRequestEvaluationResult.APPROVED));
    }

    private List<String> getValidationMessages(List<LoanRequestValidationResult> loanRequestValidationResults) {
        return loanRequestValidationResults
                .stream()
                .map(LoanRequestValidationResult::getValidationMessage)
                .collect(Collectors.toList());
    }

    private boolean checkRequestDtoIsValid(List<LoanRequestValidationResult> loanRequestValidationResults) {
        return loanRequestValidationResults
                .stream()
                .allMatch(LoanRequestValidationResult::isValid);
    }
}