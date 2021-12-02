package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.PaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @PostMapping(value="/payments")
    public ResponseEntity<?> addPayment(@RequestBody PaymentDTO paymentDTO) {

        Card card = cardRepository.findByNumber(paymentDTO.getNumber());
        Client client = clientRepository.findByCards(card);
        Account account = client.getAccounts().stream().findAny().get();

        if(paymentDTO.getAmount() == 0 || paymentDTO.getCvv() == 0 || paymentDTO.getNumber().isEmpty() || paymentDTO.getDescription().isEmpty()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if(account.getBalance() < paymentDTO.getAmount()) {
            return new ResponseEntity<>("the amount is insufficient", HttpStatus.FORBIDDEN);
        }
        if(card.getCvv() != paymentDTO.getCvv()) {
            return new ResponseEntity<>("The security code is incorrect", HttpStatus.FORBIDDEN);
        }
        Transaction transaction = new Transaction(TransactionType.DEBIT,-paymentDTO.getAmount(), paymentDTO.getDescription() + " " + account.getNumber(), LocalDateTime.now(), account.getBalance()- paymentDTO.getAmount());
        account.setBalance(account.getBalance() - paymentDTO.getAmount());
        accountRepository.save(account);
        account.addTransactions(transaction);
        transactionRepository.save(transaction);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
