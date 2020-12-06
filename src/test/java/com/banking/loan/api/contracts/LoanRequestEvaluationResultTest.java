package com.banking.loan.api.contracts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LoanRequestEvaluationResultTest {

    @ParameterizedTest
    @MethodSource("loanEvaluationResultDataSource")
    void shouldReturnLoanEvaluationResultForGivenName(String givenName, LoanRequestEvaluationResult expectedResult) {
        //given //when
        var requestEvaluationResult = LoanRequestEvaluationResult.fromString(givenName);

        //then
        assertThat(requestEvaluationResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> loanEvaluationResultDataSource() {
        return Stream.of(
                Arguments.of("REJECTED", LoanRequestEvaluationResult.REJECTED),
                Arguments.of("APPROVED", LoanRequestEvaluationResult.APPROVED)
        );
    }
}