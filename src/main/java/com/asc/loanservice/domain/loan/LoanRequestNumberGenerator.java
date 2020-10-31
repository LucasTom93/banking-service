package com.asc.loanservice.domain.loan;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
class LoanRequestNumberGenerator {

    String generateLoanRequestNumber() {
        return UUID.randomUUID().toString();
    }
}