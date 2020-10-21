package com.asc.loanservice.domain.time;

import java.time.LocalDate;

class SimpleClock implements Clock {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
