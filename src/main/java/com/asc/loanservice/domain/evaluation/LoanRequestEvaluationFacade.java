package com.asc.loanservice.domain.evaluation;

import java.util.Set;

public class LoanRequestEvaluationFacade {
    private final Set<LoanRequestEvaluationRule> loanRequestEvaluationRules;

    public LoanRequestEvaluationFacade(Set<LoanRequestEvaluationRule> loanRequestEvaluationRules) {
        this.loanRequestEvaluationRules = loanRequestEvaluationRules;
    }

    public Set<LoanRequestEvaluationRule> getLoanRequestEvaluationRules() {
        return Set.copyOf(loanRequestEvaluationRules);
    }
}