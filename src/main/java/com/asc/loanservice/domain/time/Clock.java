package com.asc.loanservice.domain.time;

import java.time.LocalDate;

public interface Clock {

    LocalDate getCurrentDate();
}
