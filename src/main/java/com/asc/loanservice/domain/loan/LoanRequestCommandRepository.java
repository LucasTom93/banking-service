package com.asc.loanservice.domain.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asc.loanservice.annotations.DomainRepository;

@DomainRepository
interface LoanRequestCommandRepository extends JpaRepository<LoanRequest, String> {
}