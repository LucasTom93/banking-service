package com.asc.loanservice.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {
    private String customerName;
    private LocalDate customerBirthday;
    private String customerTaxId;
    private BigDecimal customerMonthlyIncome;
    private BigDecimal loanAmount;
    private Integer numberOfInstallments;
    private LocalDate firstInstallmentDate;
}
