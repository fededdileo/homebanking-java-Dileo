package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @PostMapping("/clients/current/accounts")
    //@RequestMapping(path= "/api/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(Authentication authentication, @RequestParam AccountType accType) {
        Client clientGeneral = clientRepository.findByEmail(authentication.getName());
        if (clientGeneral.getAccounts().size() > 2) {
            return new ResponseEntity<>("403 Prohibited - Already Have 3 accounts", HttpStatus.FORBIDDEN);
        }
        accountRepository.save(new Account("VIN" + getRandomNumber(0 , 99999999), LocalDateTime.now(), 0, clientGeneral, accType));
        return new ResponseEntity<>("201 Account Created", HttpStatus.CREATED);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @DeleteMapping("/clients/current/accounts/{number}")
    public ResponseEntity<?> deleteAccount(Authentication authentication, @PathVariable("number") String number){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findByNumber(number);;
        /*if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }*/
        Transaction transactions = transactionRepository.findByAccount(account);
        if(transactions != null){
            transactionRepository.delete(transactions);
        }
        accountRepository.delete(account);
        return new ResponseEntity<>("Cuenta y transacciones eliminadas", HttpStatus.ACCEPTED);
    }
}
