package com.asc.loanservice.domain.loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;

class LoanRequestQueryJDBCRepository implements LoanRequestQueryRepository {
    private final JdbcOperations jdbcOperations;

    LoanRequestQueryJDBCRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Optional<LoanRequestDataDto> findByLoanNumber(String loanRequestNumber) {
        String sql = "SELECT " +
                "LR.REQUEST_NUMBER," +
                "LR.LOAN_AMOUNT," +
                "LR.NUMBER_OF_INSTALLMENTS," +
                "LR.REGISTRATION_DATE," +
                "LR.FIRST_INSTALLMENT_DATE," +
                "LR.EVALUATION_RESULT," +
                "C.NAME," +
                "C.TAX_ID," +
                "C.MONTHLY_INCOME " +
                "FROM LOAN_REQUEST LR " +
                "JOIN CUSTOMER C ON LR.CUSTOMER_ID = C.CUSTOMER_ID " +
                "WHERE LR.REQUEST_NUMBER = ?";
        var rowResultList = jdbcOperations.query(sql, new Object[]{loanRequestNumber}, new ColumnMapRowMapper());
        if (rowResultList.isEmpty()) {
            return Optional.empty();
        }
        var columnDataMap = rowResultList.get(0);
        return Optional.of(LoanRequestDataDto.Builder
                .loanRequestDataDto()
                .withLoanRequestNumber((String) columnDataMap.get("LR.REQUEST_NUMBER"))
                .withLoanAmount(new BigDecimal((String) columnDataMap.get("LR.LOAN_AMOUNT")))
                .withNumberOfInstallments((int) columnDataMap.get("LR.NUMBER_OF_INSTALLMENTS"))
                .withFirstInstallmentDate(LocalDate.parse((String) columnDataMap.get("LR.FIRST_INSTALLMENT_DATE")))
                .withEvaluationResult(LoanRequestEvaluationResult.fromString((String) columnDataMap.get("LR.EVALUATION_RESULT")))
                .withCustomerName((String) columnDataMap.get("C.NAME"))
                .withCustomerTaxId((String) columnDataMap.get("C.TAX_ID"))
                .withCustomerMonthlyIncome(new BigDecimal((String) columnDataMap.get("C.MONTHLY_INCOME")))
                .build()
        );
    }
}