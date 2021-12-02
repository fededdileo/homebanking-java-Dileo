package com.mindhub.homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private double cvv;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yy")
    private LocalDateTime fromDate, truDate;

    public CardDTO(Card cards){
        this.id = cards.getId();
        this.cardHolder = cards.getCardHolder();
        this.type = cards.getType();
        this.color = cards.getColor();
        this.number = cards.getNumber();
        this.cvv = cards.getCvv();
        this.fromDate = cards.getFromDate();
        this.truDate = cards.getTruDate();
    }

    public long getId() { return id; }
    public void setId(long id) {this.id = id;}

    public String getCardHolder() {return cardHolder;}
    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public CardType getType() {return type;}
    public void setType(CardType type) {this.type = type;}

    public CardColor getColor() {return color;}
    public void setColor(CardColor color) {this.color = color;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public double getCvv() {return cvv;}
    public void setCvv(double cvv) {this.cvv = cvv;}

    public LocalDateTime getFromDate() {return fromDate;}
    public void setFromDate(LocalDateTime fromDate) {this.fromDate = fromDate;}

    public LocalDateTime getTruDate() {return truDate;}
    public void setTruDate(LocalDateTime truDate) {this.truDate = truDate;}
}
