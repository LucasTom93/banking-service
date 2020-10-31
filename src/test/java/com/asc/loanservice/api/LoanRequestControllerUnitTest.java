package com.asc.loanservice.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestEvaluationResult;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.loan.LoanRequestApplicationService;
import com.asc.loanservice.domain.loan.LoanRequestQueryRepository;
import com.asc.loanservice.domain.loan.LoanValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class LoanRequestControllerUnitTest {
    @Mock
    private LoanRequestApplicationService loanRequestApplicationService;
    @Mock
    private LoanRequestQueryRepository loanRequestQueryRepository;
    @InjectMocks
    private LoanRequestController loanRequestController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldRegisterLoanRequest() throws Exception {
        //given
        objectMapper.registerModule(new JavaTimeModule());
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestNumber = UUID.randomUUID().toString();
        var loanRequestDto = objectMapper.readValue(getValidLoanRequestJson(), LoanRequestDto.class);
        var requestBodyJson = objectMapper.writeValueAsString(loanRequestDto);
        var loanApplicationServiceResult = createLoanRequestRegistrationResultDto(loanRequestNumber);
        when(loanRequestApplicationService.registerLoanRequest(loanRequestDto)).thenReturn(loanApplicationServiceResult);

        //when //then
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("loanRequestNumber").value(loanRequestNumber))
                .andExpect(jsonPath("evaluationResult").value("APPROVED"));
    }

    @Test
    void shouldReturnBadRequestWhenInputInvalid() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestDto = objectMapper.readValue(getInvalidLoanRequestJson(), LoanRequestDto.class);
        var requestBodyJson = objectMapper.writeValueAsString(loanRequestDto);
        when(loanRequestApplicationService.registerLoanRequest(loanRequestDto)).thenThrow(new LoanValidationException(""));

        //when //then
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnLoanRequestDataWhenExists() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestDataDto = createRequestLoanDataDto();
        var loanRequestNumber = UUID.randomUUID().toString();
        when(loanRequestQueryRepository.findByLoanNumber(loanRequestNumber)).thenReturn(Optional.of(loanRequestDataDto));

        //when //then
        mockMvc.perform(get("/api/loans/{loanNumber}", loanRequestNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotReturnLoanRequestDataWhenDoesntExist() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestNumber = UUID.randomUUID().toString();
        when(loanRequestQueryRepository.findByLoanNumber(loanRequestNumber)).thenReturn(Optional.empty());

        //when //then
        mockMvc.perform(get("/api/loans/{loanNumber}", loanRequestNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private LoanRequestDataDto createRequestLoanDataDto() {
        return LoanRequestDataDto.Builder
                .loanRequestDataDto()
                .build();
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

    private LoanRequestRegistrationResultDto createLoanRequestRegistrationResultDto(String loanRequestNumber) {
        return LoanRequestRegistrationResultDto.of(
                loanRequestNumber,
                LoanRequestEvaluationResult.APPROVED);
    }
}