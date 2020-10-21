package com.asc.loanservice.api;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;
import com.asc.loanservice.domain.loan.LoanRequestQueryRepository;

@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {

    private final LoanRequestQueryRepository loanRequestQueryRepository;

    public LoanRequestController(LoanRequestQueryRepository loanRequestQueryRepository) {
        this.loanRequestQueryRepository = loanRequestQueryRepository;
    }

    @PostMapping
    public LoanRequestRegistrationResultDto register(@RequestBody LoanRequestDto loanRequest){
        //TODO: implement
        return null;
    }

    @GetMapping("/{loanNumber}")
    public ResponseEntity<LoanRequestDataDto> getByNumber(@PathVariable String loanNumber) {
        Optional<LoanRequestDataDto> loanRequestData = loanRequestQueryRepository.findByLoanNumber(loanNumber);
        return loanRequestData
                .map(loanRequestDataDto -> ResponseEntity.ok().body(loanRequestDataDto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
