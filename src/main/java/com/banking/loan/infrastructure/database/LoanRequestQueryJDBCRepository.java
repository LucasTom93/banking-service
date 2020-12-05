package com.banking.loan.infrastructure.database;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import com.banking.loan.api.contracts.LoanRequestDataDto;

class LoanRequestQueryJDBCRepository implements LoanRequestQueryRepository {
    private final JdbcOperations jdbcOperations;

    LoanRequestQueryJDBCRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Optional<LoanRequestDataDto> findByLoanNumber(String loanRequestNumber) {
        var sqlQuery = "SELECT " +
                "LOAN_REQUEST_NUMBER," +
                "LOAN_AMOUNT," +
                "LOAN_NUMBER_OF_INSTALLMENTS," +
                "LOAN_REGISTRATION_DATE," +
                "LOAN_FIRST_INSTALLMENT_DATE," +
                "LOAN_EVALUATION_RESULT," +
                "CUSTOMER_NAME," +
                "CUSTOMER_DATE_OF_BIRTH," +
                "CUSTOMER_TAX_ID," +
                "CUSTOMER_MONTHLY_INCOME " +
                "FROM LOAN_REQUEST " +
                "WHERE LOAN_REQUEST_NUMBER = ?";

        try {
            var loanRequestDataDto = jdbcOperations.queryForObject(sqlQuery, new LoanRequestDataRowMapper(), loanRequestNumber);
            return Optional.ofNullable(loanRequestDataDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}