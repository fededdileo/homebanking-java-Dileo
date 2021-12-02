package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    //Test loans
    @Test

    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }



    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", not("Personal"))));

    }
    //Test clients
    @Test

    public void existClients(){

        List<Client> clients = clientRepository.findAll();

        assertThat(clients,is(not(empty())));

    }

    @Test

    public void existClientsEmail(){

        List<Client> clients = clientRepository.findAll();

        assertThat(clients,hasItem(hasProperty("email", not(empty()))));

    }

    //Test Accounts
    @Test

    public void existAccounts(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,is(not(empty())));

    }

    @Test

    public void existAccountNumber(){

        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,hasItem(hasProperty("number", not(empty()))));

    }

    //Test Card
    @Test

    public void existCard(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,is(not(empty())));

    }

    @Test

    public void existCardCvv(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,hasItem(hasProperty("cvv", not(empty()))));

    }

    //Test Transaction
    @Test

    public void existTransaction(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards,is(not(empty())));

    }

    @Test

    public void existTransactionAccount(){

        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions,hasItem(hasProperty("account", not(empty()))));

    }
}
