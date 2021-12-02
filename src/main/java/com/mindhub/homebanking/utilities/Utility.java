package com.mindhub.homebanking.utilities;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Utility {
    public static double calcularBalance (Set<Transaction> transactions , Account accountOrigin) {
        double balance = accountOrigin.getBalance();
        for (Transaction transaction : transactions) {
            balance = balance + transaction.getAmount();
        }
        return balance;
    }

    public static List<LocalDate> getDatesBetween(
            LocalDate startDate, LocalDate endDate) {

        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }
}
