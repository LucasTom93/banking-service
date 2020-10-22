package com.asc.loanservice.domain.loan;

import java.util.UUID;

class LoanRequestNumberGenerator {
    static String generateLoanRequestNumber() {
        return UUID.randomUUID().toString();
    }
}