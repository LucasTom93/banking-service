package com.asc.loanservice.domain.evaluation;

import java.util.Set;
import java.util.stream.Collectors;

import com.asc.loanservice.contracts.LoanRequestDto;

public class LoanRequestEvaluationFacade {
    private final Set<LoanRequestEvaluationRule> loanRequestEvaluationRules;

    public LoanRequestEvaluationFacade(Set<LoanRequestEvaluationRule> loanRequestEvaluationRules) {
        this.loanRequestEvaluationRules = loanRequestEvaluationRules;
    }

    public Set<LoanRequestEvaluationResultDetails> evaluate(LoanRequestDto loanRequestDto) {
        return loanRequestEvaluationRules
                .stream()
                .map(rule -> rule.evaluate(loanRequestDto))
                .collect(Collectors.toSet());
    }
}