package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity

public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();
    private double porcentaje;
    public Loan() { }

    public Loan(String name, double maxAmount, List<Integer> payments, double porcentaje) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.porcentaje = porcentaje;
    }
    @JsonIgnore
    public List <Client> getClients(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());

    }
    public void addClientLoan (ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }


    public long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(int maxAmount) { this.maxAmount = maxAmount; }

    public List<Integer> getPayments() { return payments; }
    public void setPayments(List<Integer> payments) { this.payments = payments; }

    public double getPorcentaje() {return porcentaje;}
    public void setPorcentaje(double porcentaje) {this.porcentaje = porcentaje;}

}
