CREATE TABLE IF NOT EXTSTS "LOAN_REQUEST"
(
    "REQUEST_NUMBER" NOT NULL VARCHAR(36) PRIMARY KEY,
    "LOAN_REQUEST_TAX" NOT NULL NUMERIC,
    "NUMBER OF INSTALLMENTS" NOT NULL INTEGER,
    "FIRST_INSTALLMENT_DATE" NOT NULL DATE
)