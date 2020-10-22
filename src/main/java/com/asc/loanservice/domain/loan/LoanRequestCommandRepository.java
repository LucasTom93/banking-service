package com.asc.loanservice.domain.loan;

import org.springframework.data.jpa.repository.JpaRepository;

interface LoanRequestCommandRepository extends JpaRepository<LoanRequest, String> {
}