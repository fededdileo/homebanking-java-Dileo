package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;


@Entity

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime creationDate;
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    private Account account;

    public Transaction () { }

    public Transaction(TransactionType type, double amount ,String description, LocalDateTime date, double balances){
        this.type= type;
        this.amount= amount;
        this.description = description;
        this.creationDate = date;
        this.balance = balances;
    }

    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}

    public Long getId() { return id; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public Account getAccount() {return account;}
    public void setAccount (Account account){this.account = account;}

    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", account=" + account +
                '}';
    }

}
