package com.banking.loan.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banking.LoanServiceApplication;
import com.banking.loan.api.contracts.LoanRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(classes = LoanServiceApplication.class)
@ActiveProfiles("TEST")
class LoanServiceE2ETest {

    @Autowired
    private LoanRequestController loanRequestController;

    @Autowired
    private JdbcOperations jdbcOperations;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
    }

    @Test
    void shouldRegisterLoanRequest() throws Exception {
        //given
        var validRequestBodyJson = prepareRequestBody(getValidLoanRequestJson());

        //when //then
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestBodyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("loanRequestNumber").isNotEmpty())
                .andExpect(jsonPath("evaluationResult").isNotEmpty());
    }

    @Test
    void shouldReturnBadRequestWhenInputInvalid() throws Exception {
        //given
        var invalidRequestBodyJson = prepareRequestBody(getInvalidLoanRequestJson());
        //when //then
        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBodyJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotFindLoanRequestDataWhenDoesntExist() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build();
        var loanRequestNumber = UUID.randomUUID().toString();

        //when //then
        mockMvc.perform(get("/api/loans/{loanNumber}", loanRequestNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnLoanRequestDataWhenExists() throws Exception {
        //given
        insertTestData();
        var loanRequestNumber = "cb6d9544-36ca-4542-b146-e5cb7ba04abb";

        //when //then
        mockMvc.perform(get("/api/loans/{loanNumber}", loanRequestNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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

    private void insertTestData() throws IOException {
        var classPathResource = new ClassPathResource("db/insert_loan_request_data.sql");
        var initDataSql = new String(Files.readAllBytes(Paths.get(classPathResource.getURI())));
        jdbcOperations.execute(initDataSql);
    }

    private String prepareRequestBody(String requestBodyJson) throws Exception {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        var loanRequestDto = objectMapper.readValue(requestBodyJson, LoanRequestDto.class);
        return objectMapper.writeValueAsString(loanRequestDto);
    }
}