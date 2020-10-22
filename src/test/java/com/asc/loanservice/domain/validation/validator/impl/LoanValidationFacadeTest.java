package com.asc.loanservice.domain.validation.validator.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import com.asc.loanservice.domain.validation.LoanRequestValidationResult;
import com.asc.loanservice.domain.validation.LoanValidationFacade;

class LoanValidationFacadeTest {
    private LoanValidationFacadeTestDataProvider testDataProvider;
    private LoanValidationFacade loanValidationFacade;

    @BeforeEach
    void setUp() {
        testDataProvider = new LoanValidationFacadeTestDataProvider();
        loanValidationFacade = new LoanValidationFacade(testDataProvider.getAllLoanRequestDataValidators());
    }

    @Test
    void shouldReturnPositiveResultsWhenLoanRequestDtoCorrect() {
        //given
        var loanDataRequestDto = testDataProvider.createCorrectLoanRequestDto();

        //when
        var loanRequestValidationResults = loanValidationFacade.validateLoanRequest(loanDataRequestDto);

        //then
        assertThatAllFieldsAreValid(loanRequestValidationResults);
    }

    @Test
    void shouldReturnNegativeResultsWhenLoanRequestDtoIsEmpty() {
        //given
        var loanDataRequestDto = testDataProvider.createEmptyLoanRequestDto();

        //when
        var loanRequestValidationResults = loanValidationFacade.validateLoanRequest(loanDataRequestDto);

        //then
        assertThatAllFieldsAreInvalid(loanRequestValidationResults);
    }

    @Test
    void shouldReturnNegativeResultsWhenForIncorrectDateValues() {
        //given
        var loanDataRequestDto = testDataProvider.createLoanRequestDtoWithIncorrectDateValues();

        //when
        var loanRequestValidationResults = loanValidationFacade.validateLoanRequest(loanDataRequestDto);

        //then
        assertThatDateFieldsAreInvalid(loanRequestValidationResults);
    }

    @Test
    void shouldReturnNegativeResultsWhenForIncorrectNumericValues() {
        //given
        var loanDataRequestDto = testDataProvider.createLoanRequestDtoWithIncorrectNumericValues();

        //when
        var loanRequestValidationResults = loanValidationFacade.validateLoanRequest(loanDataRequestDto);

        //then
        assertThatNumericFieldsAreInvalid(loanRequestValidationResults);
    }

    private void assertThatDateFieldsAreInvalid(List<LoanRequestValidationResult> loanRequestValidationResults) {
        Stream<String> validationMessages = loanRequestValidationResults
                .stream()
                .map(LoanRequestValidationResult::getValidationMessage)
                .filter(StringUtils::isNotBlank);
        assertThat(validationMessages.count()).isEqualTo(testDataProvider.getDateLoanRequestDataValidators().size());
    }

    private void assertThatNumericFieldsAreInvalid(List<LoanRequestValidationResult> loanRequestValidationResults) {
        Stream<String> validationMessages = loanRequestValidationResults
                .stream()
                .map(LoanRequestValidationResult::getValidationMessage)
                .filter(StringUtils::isNotBlank);
        assertThat(validationMessages.count()).isEqualTo(testDataProvider.getNumericLoanRequestDataValidators().size());
    }

    private void assertThatAllFieldsAreValid(List<LoanRequestValidationResult> loanRequestValidationResults) {
        loanRequestValidationResults.forEach(
                validationResult -> assertThat(validationResult
                        .isValid())
                        .overridingErrorMessage(validationResult.getValidationMessage())
                        .isTrue()
        );
    }

    private void assertThatAllFieldsAreInvalid(List<LoanRequestValidationResult> loanRequestValidationResults) {
        Stream<String> validationMessages = loanRequestValidationResults
                .stream()
                .map(LoanRequestValidationResult::getValidationMessage)
                .filter(StringUtils::isNotBlank);
        assertThat(validationMessages.count()).isEqualTo(testDataProvider.getAllLoanRequestDataValidators().size());
        loanRequestValidationResults.forEach(validationResult -> assertThat(validationResult.isValid()).isFalse());
    }
}