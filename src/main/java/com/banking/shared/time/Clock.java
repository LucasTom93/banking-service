package com.banking.shared.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Clock {

    LocalDate getCurrentDate();

    LocalDateTime getCurrentLocalDateTime();
}