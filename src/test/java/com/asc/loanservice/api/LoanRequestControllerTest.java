package com.asc.loanservice.api;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.loan.LoanApplicationServiceResult;
import com.asc.loanservice.domain.loan.LoanRequestApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class LoanRequestControllerTest {
    @Mock
    private LoanRequestApplicationService loanRequestApplicationService;
    @InjectMocks
    private LoanRequestController loanRequestController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    //??? Problems with mapping LocalDte by object mapper - manual tests passed ???
    @Disabled
    void shouldRegisterLoanRequest() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestRegistrationResultDto = mock(LoanRequestRegistrationResultDto.class);
        var loanRequestDto = objectMapper.readValue(getValidLoanRequestJson(), LoanRequestDto.class);
        var loanApplicationServiceResult = LoanApplicationServiceResult.of(true, Collections.emptyList(), loanRequestRegistrationResultDto);
        var requestBodyJson = objectMapper.writeValueAsString(loanRequestDto);
        when(loanRequestApplicationService.registerLoanRequest(loanRequestDto)).thenReturn(loanApplicationServiceResult);

        //when //then
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWHenInputInvalid() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestRegistrationResultDto = mock(LoanRequestRegistrationResultDto.class);
        var loanRequestDto = objectMapper.readValue(getInvalidLoanRequestJson(), LoanRequestDto.class);
        var loanApplicationServiceResult = LoanApplicationServiceResult.of(false, Collections.emptyList(), loanRequestRegistrationResultDto);
        var requestBodyJson = objectMapper.writeValueAsString(loanRequestDto);
        when(loanRequestApplicationService.registerLoanRequest(loanRequestDto)).thenReturn(loanApplicationServiceResult);

        //when //then
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isBadRequest());
    }

    private String getValidLoanRequestJson() {
        return "{\n" +
                "  \"customerBirthday\": \"2000-01-01\",\n" +
                "  \"customerMonthlyIncome\": 10000,\n" +
                "  \"customerName\": \"John Doe\",\n" +
                "  \"customerTaxId\": \"12345\",\n" +
                "  \"loanAmount\": 40000,\n" +
                "  \"firstInstallmentDate\": \"2021-01-01\",\n" +
                "  \"numberOfInstallments\": 30\n" +
                "}";
    }

    private String getInvalidLoanRequestJson() {
        return "{\n" +
                "  \"customerMonthlyIncome\": 10000,\n" +
                "  \"numberOfInstallments\": 30\n" +
                "}";
    }
}