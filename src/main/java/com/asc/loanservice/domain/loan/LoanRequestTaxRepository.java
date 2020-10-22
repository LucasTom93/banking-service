package com.asc.loanservice.domain.loan;

import org.springframework.data.jpa.repository.JpaRepository;

interface LoanRequestTaxRepository extends JpaRepository<LoanRequestTax, String> {
}