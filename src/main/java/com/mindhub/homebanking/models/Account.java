package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Client cliente;

    @OneToMany(mappedBy = "account",fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account() { }

    public Account(String numb,LocalDateTime date, double balanceM, Client client, AccountType accType) {
        number = numb;
        creationDate = date;
        balance = balanceM;
        cliente = client;
        accountType = accType;
    }

    public Set<Transaction> getTransactions() {return transactions;}
    public void addTransactions(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public long getId() { return id; }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}

    public Client getOwner() { return cliente; }
    public void setOwner(Client cliente) {
        this.cliente = cliente;
    }

    public AccountType getAccountType() {return accountType;}
    public void setAccountType(AccountType accountType) {this.accountType = accountType;}

}
