package com.banking.loan.infrastructure.database;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;

import com.banking.loan.api.contracts.LoanRequestDataDto;
import com.banking.loan.api.contracts.LoanRequestEvaluationResult;

class LoanRequestQueryJDBCRepository implements LoanRequestQueryRepository {
    private final JdbcOperations jdbcOperations;

    LoanRequestQueryJDBCRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Optional<LoanRequestDataDto> findByLoanNumber(String loanRequestNumber) {
        String sql = "SELECT " +
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
        var rowResultList = jdbcOperations.query(sql, new Object[]{loanRequestNumber}, new ColumnMapRowMapper());
        if (rowResultList.isEmpty()) {
            return Optional.empty();
        }
        var columnDataMap = rowResultList.get(0);
        return Optional.of(LoanRequestDataDto.Builder
                .loanRequestDataDto()
                .withLoanRequestNumber((String) columnDataMap.get("LOAN_REQUEST_NUMBER"))
                .withLoanAmount((BigDecimal) columnDataMap.get("LOAN_AMOUNT"))
                .withNumberOfInstallments((int) columnDataMap.get("LOAN_NUMBER_OF_INSTALLMENTS"))
                .withFirstInstallmentDate(((Date) columnDataMap.get("LOAN_FIRST_INSTALLMENT_DATE")).toLocalDate())
                .withRegistrationDate(((Timestamp) columnDataMap.get("LOAN_REGISTRATION_DATE")).toLocalDateTime())
                .withEvaluationResult(LoanRequestEvaluationResult.fromString((String) columnDataMap.get("LOAN_EVALUATION_RESULT")))
                .withCustomerName((String) columnDataMap.get("CUSTOMER_NAME"))
                .withCustomerTaxId((String) columnDataMap.get("CUSTOMER_TAX_ID"))
                .withCustomerMonthlyIncome((BigDecimal) columnDataMap.get("CUSTOMER_MONTHLY_INCOME"))
                .withCustomerBirthday(((Date) columnDataMap.get("CUSTOMER_DATE_OF_BIRTH")).toLocalDate())
                .build()
        );
    }
}