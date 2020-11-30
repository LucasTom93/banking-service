package com.banking.loan.domain.evaluation;

import java.util.Set;

public class LoanRequestEvaluationPolicyProvider {
    private final Set<LoanRequestEvaluationPolicy> loanRequestEvaluationPolicies;

    public LoanRequestEvaluationPolicyProvider(Set<LoanRequestEvaluationPolicy> loanRequestEvaluationPolicies) {
        this.loanRequestEvaluationPolicies = loanRequestEvaluationPolicies;
    }

    public Set<LoanRequestEvaluationPolicy> getLoanRequestEvaluationPolicies() {
        return Set.copyOf(loanRequestEvaluationPolicies);
    }
}