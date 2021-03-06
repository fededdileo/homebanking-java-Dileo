package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    private double totalAmount;
    private String account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="loan_id")
    private Loan loan;

    public ClientLoan() { }

    public ClientLoan(double amount, int payments, Client client, Loan loan, String account) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
        this.totalAmount = amount + (amount * ( loan.getPorcentaje() / 100));
        this.account = account;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Integer getPayments() { return payments; }
    public void setPayments(Integer payments) { this.payments = payments; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public double getTotalAmount() {return totalAmount;}
    public void setTotalAmount(double totalAmount) {this.totalAmount = totalAmount;}

    public String getAccount() {return account;}
    public void setAccount(String account) {this.account = account;}
}
