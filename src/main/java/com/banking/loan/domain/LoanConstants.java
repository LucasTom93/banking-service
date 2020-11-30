package com.banking.loan.domain;

import java.math.BigDecimal;

public class LoanConstants {
    //All these constants could be taken from system configuration with theirs defaults
    public static final int BIG_DECIMAL_SCALE = 5;
    public static final BigDecimal PERCENTAGE_RISK_THRESHOLD = BigDecimal.valueOf(0.15);
    public static final BigDecimal NUMBER_OF_INSTALLMENTS_IN_YEAR = BigDecimal.valueOf(12);
    public static final BigDecimal ANNUAL_LOAN_TAX_RATE = BigDecimal.valueOf(0.04);
}