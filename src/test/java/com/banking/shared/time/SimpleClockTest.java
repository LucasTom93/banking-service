package com.banking.shared.time;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SimpleClockTest {

    private final Clock clock = new ClockConfiguration().clock();

    @Test
    void shouldGetCurrentDate() {
        //given //when
        var currentLocalDate = clock.getCurrentDate();

        //then
        assertThat(currentLocalDate).isNotNull();
    }

    @Test
    void shouldGetCurrentLocalDateTime() {
        //given //when
        var currentLocalDateTime = clock.getCurrentLocalDateTime();

        //then
        assertThat(currentLocalDateTime).isNotNull();
    }
}