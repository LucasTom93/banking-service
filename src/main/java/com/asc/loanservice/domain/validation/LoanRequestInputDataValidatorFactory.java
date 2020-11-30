package com.asc.loanservice.domain.validation;

import java.time.LocalDate;

class LoanRequestInputDataValidatorFactory {

    static LoanRequestIncomingDataValidator customerDateOfBirthValidator(LocalDate currentDate) {
        return new CustomerDateOfBirthValidator(currentDate);
    }

    static LoanRequestIncomingDataValidator customerMonthlyIncomeValidator() {
        return new CustomerMonthlyIncomeValidator();
    }

    static LoanRequestIncomingDataValidator customerNameValidator() {
        return new CustomerNameValidator();
    }

    static LoanRequestIncomingDataValidator customerTaxIdValidator() {
        return new CustomerTaxIdValidator();
    }

    static LoanRequestIncomingDataValidator firstInstallmentDateValidator(LocalDate currentDate) {
        return new FirstInstallmentDateValidator(currentDate);
    }

    static LoanRequestIncomingDataValidator loanAmountValidator() {
        return new LoanAmountValidator();
    }

    static LoanRequestIncomingDataValidator numberOfInstallmentsValidator() {
        return new NumberOfInstallmentsValidator();
    }
}