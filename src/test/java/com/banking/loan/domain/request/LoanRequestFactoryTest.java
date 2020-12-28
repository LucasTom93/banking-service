package com.banking.loan.domain.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.loan.api.contracts.LoanRequestDto;
import com.banking.shared.identity.IdentityGenerator;
import com.banking.shared.time.Clock;

@ExtendWith(MockitoExtension.class)
class LoanRequestFactoryTest {

    @Mock
    private Clock clock;
    @Mock
    private IdentityGenerator identityGenerator;
    @InjectMocks
    private LoanRequestFactory loanRequestFactory;

    @Test
    void shouldCreateLoanRequestEntity() {
        //given
        var currentDateTime = LocalDateTime.of(2020, Month.JANUARY, 1, 1, 1, 1, 1);
        var loanRequestNumber = UUID.randomUUID().toString();
        var loanRequestDto = createLoanRequestDto();
        when(identityGenerator.generateStringId()).thenReturn(loanRequestNumber);
        when(clock.getCurrentLocalDateTime()).thenReturn(currentDateTime);

        //when
        var loanRequest = loanRequestFactory.createLoanRequest(loanRequestDto);

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
                loanRequestDto.getLoanAmount(),
                loanRequestDto.getNumberOfInstallments(),
                loanRequestDto.getFirstInstallmentDate(),
                currentDateTime,
                loanRequestDto.getCustomerName(),
                loanRequestDto.getCustomerBirthday(),
                loanRequestDto.getCustomerMonthlyIncome(),
                loanRequestDto.getCustomerTaxId(),
                null
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