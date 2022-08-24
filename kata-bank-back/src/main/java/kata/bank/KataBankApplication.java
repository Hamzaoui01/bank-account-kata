package kata.bank;

import kata.bank.entity.Account;
import kata.bank.repository.AccountRepository;
import kata.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KataBankApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KataBankApplication.class, args);
	}


	@Autowired
	AccountRepository accountRepository;

	@Autowired
	BankService bankService;

	@Override
	public void run(String... args) throws Exception {

		accountRepository.save(Account.builder()
				.number("001")
				.build());
		accountRepository.save(Account.builder()
				.number("002")
				.build());
		accountRepository.save(Account.builder()
				.number("003")
				.build());
	}
}
