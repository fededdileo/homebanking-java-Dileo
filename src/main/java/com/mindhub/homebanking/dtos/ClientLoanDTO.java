package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {
    private long id;
    private long idLoan;
    private double amount;
    private int payments;
    private String nameLoan;
    private double totalAmount;
    private String account;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.nameLoan = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.totalAmount = clientLoan.getTotalAmount();
        this.account = clientLoan.getAccount();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getIdLoan() { return idLoan; }
    public void setIdLoan(long idLoan) { this.idLoan = idLoan; }

    public String getNameLoan() { return nameLoan; }
    public void setNameLoan(String nameLoan) { this.nameLoan = nameLoan; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public int getPayments() { return payments; }
    public void setPayments(int payments) { this.payments = payments; }

    public double getTotalAmount() {return totalAmount;}
    public void setTotalAmount(double totalAmount) {this.totalAmount = totalAmount;}

    public String getAccount() {return account;}
    public void setAccount(String account) {this.account = account;}
}
