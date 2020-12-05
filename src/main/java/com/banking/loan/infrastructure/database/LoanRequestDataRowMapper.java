package com.banking.loan.infrastructure.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.banking.loan.api.contracts.LoanRequestDataDto;
import com.banking.loan.api.contracts.LoanRequestEvaluationResult;

class LoanRequestDataRowMapper implements RowMapper<LoanRequestDataDto> {

    @Override
    public LoanRequestDataDto mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return LoanRequestDataDto.Builder
                .loanRequestDataDto()
                .withLoanRequestNumber(resultSet.getString("LOAN_REQUEST_NUMBER"))
                .withLoanAmount(resultSet.getBigDecimal("LOAN_AMOUNT"))
                .withNumberOfInstallments(resultSet.getInt("LOAN_NUMBER_OF_INSTALLMENTS"))
                .withFirstInstallmentDate(resultSet.getDate("LOAN_FIRST_INSTALLMENT_DATE").toLocalDate())
                .withRegistrationDate(resultSet.getTimestamp("LOAN_REGISTRATION_DATE").toLocalDateTime())
                .withEvaluationResult(LoanRequestEvaluationResult.fromString(resultSet.getString("LOAN_EVALUATION_RESULT")))
                .withCustomerName(resultSet.getString("CUSTOMER_NAME"))
                .withCustomerTaxId(resultSet.getString("CUSTOMER_TAX_ID"))
                .withCustomerMonthlyIncome(resultSet.getBigDecimal("CUSTOMER_MONTHLY_INCOME"))
                .withCustomerBirthday(resultSet.getDate("CUSTOMER_DATE_OF_BIRTH").toLocalDate())
                .build();
    }
}