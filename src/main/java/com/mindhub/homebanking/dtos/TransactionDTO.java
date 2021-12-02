package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime creationDate;
    private double balance;

    public TransactionDTO(Transaction transactions) {
        this.id = transactions.getId();
        this.type = transactions.getType();
        this.amount = transactions.getAmount();
        this.description = transactions.getDescription();
        this.creationDate = transactions.getCreationDate();
        this.balance = transactions.getBalance();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
}
