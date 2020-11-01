package com.asc.loanservice.domain.loan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.domain.evaluation.EvaluationData;
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
        when(loanRequestEvaluationRule.evaluate(any(EvaluationData.class))).thenReturn(loanRequestEvaluationResultDetails);
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

    @ParameterizedTest
    @MethodSource("failingValidationDataSource")
    void shouldThrowLoanValidationExceptionWhenInputDataInvalid(Callable<?> throwableAction) {
        assertThatThrownBy(throwableAction::call).isInstanceOf(LoanValidationException.class);
    }

    private static Stream<Arguments> failingValidationDataSource() {
        return Stream.of(
                Arguments.of((Callable<CustomerDateOfBirth>) () -> CustomerDateOfBirth.of(null, null)),
                Arguments.of((Callable<CustomerMonthlyIncome>) () -> CustomerMonthlyIncome.of(null)),
                Arguments.of((Callable<CustomerMonthlyIncome>) () -> CustomerMonthlyIncome.of(new BigDecimal(-1))),
                Arguments.of((Callable<CustomerName>) () -> CustomerName.of(null)),
                Arguments.of((Callable<CustomerTaxId>) () -> CustomerTaxId.of(null)),
                Arguments.of((Callable<FirstInstallmentDate>) () -> FirstInstallmentDate.of(null, null)),
                Arguments.of((Callable<FirstInstallmentDate>) () -> FirstInstallmentDate.of(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1))),
                Arguments.of((Callable<LoanAmount>) () -> LoanAmount.of(null)),
                Arguments.of((Callable<LoanAmount>) () -> LoanAmount.of(new BigDecimal(0))),
                Arguments.of((Callable<NumberOfInstallments>) () -> NumberOfInstallments.of(null)),
                Arguments.of((Callable<NumberOfInstallments>) () -> NumberOfInstallments.of(0))
        );
    }
}