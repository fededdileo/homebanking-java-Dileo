package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/loan")
    public List<LoanDTO> getLoans (){
        return this.loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/loan/")
    public ResponseEntity<?> createLoan(Authentication authentication, @RequestParam String name, @RequestParam double maxAmount, @RequestParam List<Integer> payments, @RequestParam double fee){

        if(maxAmount == 0 || name.isEmpty()  || fee == 0){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        loanRepository.save(new Loan(name,maxAmount,payments,fee));

        return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("/clients/current/loans")
    public ResponseEntity<?> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Account accountDest = this.accountRepository.findByNumber(loanApplicationDTO.getAccountNumber());
        Loan loan = this.loanRepository.findById(loanApplicationDTO.getIdLoan());
        Client client = this.clientRepository.findByEmail(authentication.getName());

        if(loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0.00 || loanApplicationDTO.getAccountNumber().isEmpty()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (loan.getMaxAmount() < loanApplicationDTO.getAmount() ){
            return new ResponseEntity<>("Ammount error", HttpStatus.FORBIDDEN);
        }

        if(accountDest == null){
            return new ResponseEntity<>("Account doesn't exist",HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Payments Error",HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(accountDest)){
            return new ResponseEntity<>("client don't have this account", HttpStatus.FORBIDDEN);
        }
        clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getAmount() +loanApplicationDTO.getAmount() * loan.getPorcentaje(),loanApplicationDTO.getPayments(),client,loan,accountDest.getNumber()));
        transactionRepository.save(new Transaction(TransactionType.CREDIT,loanApplicationDTO.getAmount(),loan.getName()+ " " + "Loan Approved",LocalDateTime.now(),accountDest.getBalance() + loanApplicationDTO.getAmount()));
        accountDest.setBalance(accountDest.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save(accountDest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
