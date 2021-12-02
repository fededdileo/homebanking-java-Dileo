package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository ClientRepository, AccountRepository AccountRepository, TransactionRepository TransactionRepository, LoanRepository LoanRepository, ClientLoanRepository ClientLoanRepository, CardRepository CardRepository) {
		return (args) -> {

			Client client1 = new Client("Melba","Morel","melba@mindhub.com",passwordEncoder.encode("123456"));
			Client client2 = new Client("Federico","Dileo","fdileo97@gmail.com",passwordEncoder.encode("654321"));
			Client admin= new Client("admin","admin","admin@admin.com",passwordEncoder.encode("admin"));

			Account account1 = new Account("VIN001", LocalDateTime.now(),5000,client1,AccountType.CURRENT);
			Account account2 = new Account("VIN002",LocalDateTime.now().plusDays(1),7500,client1,AccountType.SAVINGS);
			Account account3 = new Account("VIN003",LocalDateTime.now(),10000,client2,AccountType.CURRENT);
			Account account4 = new Account("VIN004",LocalDateTime.now().plusDays(1),9000,client2,AccountType.SAVINGS);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT,3000,"Transaction",LocalDateTime.now(), account1.getBalance() + 3000);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,-1500,"Car Insurance",LocalDateTime.now(),account1.getBalance() - 1500);
			Transaction transaction3 = new Transaction(TransactionType.DEBIT,1000,"Transaction",LocalDateTime.now(),account2.getBalance() + 1000);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,-2000,"Car Insurance",LocalDateTime.now(), account2.getBalance() - 2000);
			Transaction transaction5 = new Transaction(TransactionType.DEBIT,6000,"Transaction",LocalDateTime.now(),account3.getBalance() + 6000);
			Transaction transaction6 = new Transaction(TransactionType.CREDIT,-2000,"Car Insurance",LocalDateTime.now(), account3.getBalance() - 2000);
			Transaction transaction7 = new Transaction(TransactionType.DEBIT,5000,"Transaction",LocalDateTime.now(),account4.getBalance() + 5000);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT,-2000,"Car Insurance",LocalDateTime.now(), account4.getBalance() - 2000);

			List<Integer> payments1 = Arrays.asList(12,24,36,48,60);
			List<Integer> payments2 = Arrays.asList(6,12,24);
			List<Integer> payments3 = Arrays.asList(6,12,24,36);
			Loan hipotecario = new Loan("Mortgage", 500000, payments1,0.30);
			Loan personal = new Loan("Personal",100000,payments2,0.15);
			Loan automotriz= new Loan("Car-Loan",300000,payments3,0.20);

			ClientLoan clientLoan1 = new ClientLoan(400000,hipotecario.getPayments().get(4), client1, hipotecario,account1.getNumber());
			ClientLoan clientLoan2 = new ClientLoan(50000,personal.getPayments().get(1), client1, personal,account2.getNumber());
			ClientLoan clientLoan3 = new ClientLoan(100000,personal.getPayments().get(2), client2, personal,account3.getNumber());
			ClientLoan clientLoan4 = new ClientLoan(200000,automotriz.getPayments().get(3),client2, automotriz,account4.getNumber());

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"4275-3156-0372-5493",747,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,"5412-1234-5678-9010",941,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastName(),CardType.CREDIT,CardColor.SILVER,"4000-1674-9456-3487",343,LocalDateTime.now(),LocalDateTime.now().plusYears(5));

			account1.addTransactions(transaction1);
			account1.addTransactions(transaction2);
			account2.addTransactions(transaction3);
			account2.addTransactions(transaction4);
			account3.addTransactions(transaction5);
			account3.addTransactions(transaction6);
			account4.addTransactions(transaction7);
			account4.addTransactions(transaction8);

			client1.addAccounts(account1);
			client1.addAccounts(account2);
			client2.addAccounts(account3);
			client2.addAccounts(account4);

			client1.addClientLoans(clientLoan1);
			client1.addClientLoans(clientLoan2);
			client2.addClientLoans(clientLoan3);
			client2.addClientLoans(clientLoan4);

			hipotecario.addClientLoan(clientLoan1);
			personal.addClientLoan(clientLoan2);
			personal.addClientLoan(clientLoan3);
			automotriz.addClientLoan(clientLoan4);


			client1.addCards(card1);
			client1.addCards(card2);
			client2.addCards(card3);

			ClientRepository.save(client1);
			ClientRepository.save(client2);
			ClientRepository.save(admin);

			AccountRepository.save(account1);
			AccountRepository.save(account2);
			AccountRepository.save(account3);
			AccountRepository.save(account4);

			TransactionRepository.save(transaction1);
			TransactionRepository.save(transaction2);
			TransactionRepository.save(transaction3);
			TransactionRepository.save(transaction4);
			TransactionRepository.save(transaction5);
			TransactionRepository.save(transaction6);
			TransactionRepository.save(transaction7);
			TransactionRepository.save(transaction8);


			LoanRepository.save(hipotecario);
			LoanRepository.save(personal);
			LoanRepository.save(automotriz);

			ClientLoanRepository.save(clientLoan1);
			ClientLoanRepository.save(clientLoan2);
			ClientLoanRepository.save(clientLoan3);
			ClientLoanRepository.save(clientLoan4);

			CardRepository.save(card1);
			CardRepository.save(card2);
			CardRepository.save(card3);
		};

	}

}
