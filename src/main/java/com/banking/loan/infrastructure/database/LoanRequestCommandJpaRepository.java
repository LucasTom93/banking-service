package com.banking.loan.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.loan.domain.LoanRequest;
import com.banking.loan.domain.LoanRequestCommandRepository;
import com.banking.shared.domain.annotations.DomainRepository;

@DomainRepository
class LoanRequestCommandJpaRepository implements LoanRequestCommandRepository {

    private final LoanRequestCommandSpringDataJpaRepository loanRequestCommandSpringDataJpaRepository;

    LoanRequestCommandJpaRepository(LoanRequestCommandSpringDataJpaRepository loanRequestCommandSpringDataJpaRepository) {
        this.loanRequestCommandSpringDataJpaRepository = loanRequestCommandSpringDataJpaRepository;
    }

    @Override
    public void save(LoanRequest loanRequest) {
        loanRequestCommandSpringDataJpaRepository.save(loanRequest);
    }

}

interface LoanRequestCommandSpringDataJpaRepository extends JpaRepository<LoanRequest, String> {
}