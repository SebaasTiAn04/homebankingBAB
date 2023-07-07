package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.Enums.AccountType;
import com.mindhub.homebanking.models.Enums.CardColor;
import com.mindhub.homebanking.models.Enums.CardType;
import com.mindhub.homebanking.models.Enums.TransactionType;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomeBankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientService clientService, AccountService accountService,
									  TransactionService transactionService , LoanService loanService,
									  ClientLoanService clientLoanService, CardService cardService) {
		return (args) -> {
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba") );
			Client Jack = new Client("Jack", "Bauer", "jack@gmail.com", passwordEncoder.encode("222222222"));
			Client Chloe = new Client("Chloe", "O'Brian", "Chloe@gmail.com", passwordEncoder.encode("3333333"));
			Client Kim = new Client("Kim", "Bauer", "Kim@gmail.com", passwordEncoder.encode("44444444"));
			Client David = new Client("David", "Palmer", "David@gmail.com", passwordEncoder.encode("555555555"));
			Client Michelle = new Client("Michelle", "Dessler", "Michelle@gmail.com", passwordEncoder.encode("66666666"));
			// save a couple of customers
			clientService.saveClient(melba);
			clientService.saveClient(Jack);
			clientService.saveClient(Chloe);
			clientService.saveClient(Kim);
			clientService.saveClient(David);
			clientService.saveClient(Michelle);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT, 6000.0, "COTO", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 10000.0, "Lavarropa Automatico", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, 650.0, "Peluqueria", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 2000.0, "YOUTUBE PREMIUN", LocalDateTime.now());
			Transaction transaction5 = new Transaction(TransactionType.DEBIT, 1200.0, "Restaurante", LocalDateTime.now());
			Transaction transaction6 = new Transaction(TransactionType.CREDIT, 14923.0, "Samsung A51", LocalDateTime.now());
			Transaction transaction7 = new Transaction(TransactionType.DEBIT, 223.0, "Cine", LocalDateTime.now());


			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000D, true, AccountType.SAVINGS);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 75000D, true, AccountType.SAVINGS);

			melba.addAccount(account1);
			melba.addAccount(account2);

			accountService.saveAccount(account1);
			accountService.saveAccount(account2);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);

			account2.addTransaction(transaction4);
			account2.addTransaction(transaction5);
			account2.addTransaction(transaction6);
			account2.addTransaction(transaction7);

			transactionService.saveTransaction(transaction1);
			transactionService.saveTransaction(transaction2);
			transactionService.saveTransaction(transaction3);
			transactionService.saveTransaction(transaction4);
			transactionService.saveTransaction(transaction5);
			transactionService.saveTransaction(transaction6);
			transactionService.saveTransaction(transaction7);

			List<Integer> mortgagePayment =  List.of(12,24,36,48,60);
			List<Integer> personalPayment =  List.of(6,12,24);
			List<Integer> carLoanPayment =  List.of(6,12,24,36);

			Loan mortgage = new Loan("mortgage", 500000D,1.5, mortgagePayment);
			Loan personal = new Loan("personal", 100000D,1.7, personalPayment);
			Loan carLoan = new Loan("carLoan", 300000D,1.9, carLoanPayment);



		 	ClientLoan clientLoan1 = new ClientLoan(400000D, mortgagePayment.get(4), LocalDateTime.now().plusMonths(1));
			ClientLoan clientLoan2 = new ClientLoan(50000D, personalPayment.get(1), LocalDateTime.now().plusMonths(1));
			ClientLoan clientLoan3 = new ClientLoan(100000D, personalPayment.get(2), LocalDateTime.now().plusMonths(1));
			ClientLoan clientLoan4 = new ClientLoan(200000D, carLoanPayment.get(3), LocalDateTime.now().plusMonths(1));

			mortgage.addClientLoan(clientLoan1);
			personal.addClientLoan(clientLoan2);
			personal.addClientLoan(clientLoan3);
			carLoan.addClientLoan(clientLoan4);


			melba.addClientLoan(clientLoan1);
			melba.addClientLoan(clientLoan2);
			Jack.addClientLoan(clientLoan3);
			Jack.addClientLoan(clientLoan4);


			loanService.saveLoan(mortgage);
			loanService.saveLoan(personal);
			loanService.saveLoan(carLoan);


			clientLoanService.saveClientLoan(clientLoan1);
			clientLoanService.saveClientLoan(clientLoan2);
			clientLoanService.saveClientLoan(clientLoan3);
			clientLoanService.saveClientLoan(clientLoan4);


			Card card1 = new Card(CardType.DEBIT, "2232-4322-4443", CardColor.GOLD, melba.getFirstName() + " " + melba.getLastName(), 990, LocalDate.now(), LocalDate.now().plusYears(5), true);
			Card card2 = new Card(CardType.CREDIT, "2234-6745-5523", CardColor.TITANIUM, melba.getFirstName() + " " + melba.getLastName(), 750, LocalDate.now(), LocalDate.now().plusYears(5), true);

			melba.addCard(card1);
			melba.addCard(card2);
			cardService.saveCard(card1);
			cardService.saveCard(card2);
		};
	}
}
