package com.banking.loan.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.loan.application.LoanRequestApplicationService;
import com.banking.loan.application.validation.LoanValidationException;
import com.banking.loan.infrastructure.LoanRequestQueryRepository;
import com.banking.shared.contracts.LoanRequestDataDto;
import com.banking.shared.contracts.LoanRequestDto;

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
    public ResponseEntity<?> register(@RequestBody LoanRequestDto loanRequest) {
        try {
            var loanRequestRegistrationResultDto = loanRequestApplicationService.registerLoanRequest(loanRequest);
            return ResponseEntity.ok().body(loanRequestRegistrationResultDto);
        } catch (LoanValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationMessage());
        }
    }

    @GetMapping("/{loanNumber}")
    public ResponseEntity<LoanRequestDataDto> getByNumber(@PathVariable String loanNumber) {
        var loanRequestDataOpt = loanRequestQueryRepository.findByLoanNumber(loanNumber);
        return loanRequestDataOpt
                .map(loanRequestDataDto -> ResponseEntity.ok().body(loanRequestDataDto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}