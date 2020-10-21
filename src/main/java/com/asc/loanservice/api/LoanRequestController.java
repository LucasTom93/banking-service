package com.asc.loanservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asc.loanservice.contracts.LoanRequestDataDto;
import com.asc.loanservice.contracts.LoanRequestDto;
import com.asc.loanservice.contracts.LoanRequestRegistrationResultDto;

@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {

    @PostMapping
    public LoanRequestRegistrationResultDto register(@RequestBody LoanRequestDto loanRequest){
        //TODO: implement
        return null;
    }

    @GetMapping("/{loanNumber}")
    public LoanRequestDataDto getByNumber(@PathVariable("loanNumber") String loanNumber){
        //TODO: implement
        return null;
    }
}
