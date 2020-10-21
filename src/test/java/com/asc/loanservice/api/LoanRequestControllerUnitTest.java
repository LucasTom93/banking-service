package com.asc.loanservice.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.domain.loan.LoanConfiguration;
import com.asc.loanservice.domain.loan.LoanRequestQueryRepository;

class LoanRequestControllerUnitTest {

    private final LoanRequestQueryRepository loanRequestQueryRepository = new LoanConfiguration().loanRequestQueryHashMapRepository();
    private final LoanRequestController loanRequestController = new LoanRequestController(loanRequestQueryRepository);

    @Test
    void shouldReturnNotFoundResponseWhenLoanRequestDoesntExist() {
        //given
        var loanNumber = UUID.randomUUID().toString();

        //when
        ResponseEntity<LoanRequestDataDto> responseEntity = loanRequestController.getByNumber(loanNumber);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnLoanRequestDataWhenExist() {
        //given
        //TODO: Prepare and save simple LoanRequest to in-memory database
        var loanNumber = "1234";

        //when
        ResponseEntity<LoanRequestDataDto> responseEntity = loanRequestController.getByNumber(loanNumber);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}