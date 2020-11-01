package com.asc.loanservice.domain.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationFacade;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationResultDetails;
import com.asc.loanservice.domain.evaluation.LoanRequestEvaluationRule;
import com.asc.loanservice.domain.time.Clock;

@ExtendWith(MockitoExtension.class)
class LoanRequestFactoryTest {

    @Mock
    private Clock clock;
    @Mock
    private LoanRequestNumberGenerator loanRequestNumberGenerator;
    @Mock
    private LoanRequestEvaluationFacade loanRequestEvaluationFacade;
    @InjectMocks
    private LoanRequestFactory loanRequestFactory;

    @Test
    void shouldCreateLoanRequestEntity() throws LoanValidationException {
        //given
        var currentDate = LocalDate.of(2020, 10, 10);
        var currentDateTime = LocalDateTime.of(2020, 1, 1, 1, 1, 1, 1);
        var loanRequestNumber = UUID.randomUUID().toString();
        var loanRequestDto = createLoanRequestDto();
        var loanRequestEvaluationRule = mock(LoanRequestEvaluationRule.class);
        var loanRequestEvaluationResultDetails = LoanRequestEvaluationResultDetails.of(LoanRequestEvaluationResult.APPROVED, "Passed");
        when(loanRequestEvaluationRule.evaluate(loanRequestDto)).thenReturn(loanRequestEvaluationResultDetails);
        when(loanRequestNumberGenerator.generateLoanRequestNumber()).thenReturn(loanRequestNumber);
        when(clock.getCurrentDate()).thenReturn(currentDate);
        when(clock.getCurrentLocalDateTime()).thenReturn(currentDateTime);
        when(loanRequestEvaluationFacade.getLoanRequestEvaluationRules()).thenReturn(Set.of(loanRequestEvaluationRule));

        //when
        var loanRequest = loanRequestFactory.createEvaluatedLoanRequest(loanRequestDto);

        //then
        assertThat(loanRequest).extracting(
                "loanRequestNumber",
                "loanAmount",
                "numberOfInstallments",
                "firstInstallmentDate",
                "registrationDate",
                "customerName",
                "customerDateOfBirth",
                "customerMonthlyIncome",
                "customerTaxId",
                "loanRequestEvaluationResult"
        ).containsExactly(
                loanRequestNumber,
                LoanAmount.of(loanRequestDto.getLoanAmount()),
                NumberOfInstallments.of(loanRequestDto.getNumberOfInstallments()),
                FirstInstallmentDate.of(loanRequestDto.getFirstInstallmentDate(), currentDate),
                currentDateTime,
                CustomerName.of(loanRequestDto.getCustomerName()),
                CustomerDateOfBirth.of(loanRequestDto.getCustomerBirthday(), currentDate),
                CustomerMonthlyIncome.of(loanRequestDto.getCustomerMonthlyIncome()),
                CustomerTaxId.of(loanRequestDto.getCustomerTaxId()),
                loanRequestEvaluationResultDetails.getLoanRequestEvaluationResult()
        );
    }

    private LoanRequestDto createLoanRequestDto() {
        return LoanRequestDto.Builder
                .loanRequestDto()
                .withCustomerBirthday(LocalDate.of(2000, 1, 1))
                .withCustomerMonthlyIncome(BigDecimal.valueOf(10000))
                .withFirstInstallmentDate(LocalDate.of(2021, 1, 1))
                .withNumberOfInstallments(10)
                .withCustomerName("Joe Doe")
                .withCustomerTaxId(UUID.randomUUID().toString())
                .withLoanAmount(BigDecimal.valueOf(1000))
                .build();
    }
}