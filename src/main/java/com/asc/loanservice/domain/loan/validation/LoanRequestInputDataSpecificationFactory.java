package com.asc.loanservice.domain.loan.validation;

import java.time.LocalDate;

class LoanRequestInputDataSpecificationFactory {

    static LoanRequestInputDataSpecification customerDateOfBirthSpecification(LocalDate currentDate) {
        return new CustomerDateOfBirthSpecification(currentDate);
    }

    static LoanRequestInputDataSpecification customerMonthlyIncomeSpecification() {
        return new CustomerMonthlyIncomeSpecification();
    }

    static LoanRequestInputDataSpecification customerNameSpecification() {
        return new CustomerNameSpecification();
    }

    static LoanRequestInputDataSpecification customerTaxIdSpecification() {
        return new CustomerTaxIdSpecification();
    }

    static LoanRequestInputDataSpecification firstInstallmentDateSpecification(LocalDate currentDate) {
        return new FirstInstallmentDateSpecification(currentDate);
    }

    static LoanRequestInputDataSpecification loanAmountSpecification() {
        return new LoanAmountSpecification();
    }

    static LoanRequestInputDataSpecification numberOfInstallmentsSpecification() {
        return new NumberOfInstallmentsSpecification();
    }
}