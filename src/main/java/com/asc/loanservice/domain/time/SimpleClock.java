package com.asc.loanservice.domain.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

class SimpleClock implements Clock {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }
}