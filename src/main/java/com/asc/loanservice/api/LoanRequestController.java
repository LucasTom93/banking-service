package com.asc.loanservice.api;

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
import com.asc.loanservice.domain.loan.LoanRequestApplicationService;
import com.asc.loanservice.domain.loan.LoanRequestQueryRepository;

@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {
    private final LoanRequestQueryRepository loanRequestQueryRepository;
    private final LoanRequestApplicationService loanRequestApplicationService;

    public LoanRequestController(LoanRequestQueryRepository loanRequestQueryRepository, LoanRequestApplicationService loanRequestApplicationService) {
        this.loanRequestQueryRepository = loanRequestQueryRepository;
        this.loanRequestApplicationService = loanRequestApplicationService;
    }

    @PostMapping
    public LoanRequestRegistrationResultDto register(@RequestBody LoanRequestDto loanRequest) {
        //TODO: implement
        return null;
    }

    @GetMapping("/{loanNumber}")
    public ResponseEntity<LoanRequestDataDto> getByNumber(@PathVariable String loanNumber) {
        var loanRequestDataOpt = loanRequestQueryRepository.findByLoanNumber(loanNumber);
        return loanRequestDataOpt
                .map(loanRequestDataDto -> ResponseEntity.ok().body(loanRequestDataDto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
