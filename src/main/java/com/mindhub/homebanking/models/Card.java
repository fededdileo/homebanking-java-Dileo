package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity


public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private double cvv;
    private LocalDateTime fromDate;
    private LocalDateTime truDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card () { }

    public Card(String cardHolder, CardType type, CardColor color, String number, double cvv, LocalDateTime fromDate, LocalDateTime truDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.truDate = truDate;
    }

    public long getId() {return id; }
    public void setId(long id) { this.id = id; }

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public CardType getType() { return type; }
    public void setType(CardType type) { this.type = type; }

    public CardColor getColor() { return color; }
    public void setColor(CardColor color) { this.color = color; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public double getCvv() { return cvv; }
    public void setCvv(double cvv) { this.cvv = cvv; }

    public LocalDateTime getFromDate() { return fromDate; }
    public void setFromDate(LocalDateTime fromDate) { this.fromDate = fromDate; }

    public LocalDateTime getTruDate() { return truDate; }
    public void setTruDate(LocalDateTime truDate) { this.truDate = truDate; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client;}
}
