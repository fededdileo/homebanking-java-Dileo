package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    Set <TransactionDTO> transactions = new HashSet<>();
    private AccountType accountType;

    public AccountDTO(Account accounts) {
        this.id = accounts.getId();
        this.number = accounts.getNumber();
        this.creationDate = accounts.getCreationDate();
        this.balance = accounts.getBalance();
        this.transactions = accounts.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        this.accountType = accounts.getAccountType();
    }

    public long getId() { return id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public Set<TransactionDTO> getTransactions() { return transactions; }
    public void setTransactions(Set<TransactionDTO> transactions) { this.transactions = transactions; }

    public AccountType getAccountType() {return accountType;}
    public void setAccountType(AccountType accountType) {this.accountType = accountType;}
}
