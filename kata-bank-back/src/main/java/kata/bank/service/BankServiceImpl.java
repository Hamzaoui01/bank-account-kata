package kata.bank.service;

import kata.bank.entity.Account;
import kata.bank.entity.Operation;
import kata.bank.exceptions.AccountNotFoundException;
import kata.bank.repository.AccountRepository;
import kata.bank.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static kata.bank.enums.OperationType.DEBIT;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService{

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Override
    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    @Transactional
    public boolean deposit(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
            operationRepository.save(Operation.builder()
                    .account(account)
                    .amount(amount)
                    .type(DEBIT.getCode())
                    .dateTime(LocalDateTime.now())
                    .build());
            accountRepository.save(Account.builder().number(accountNumber).balance(account.getBalance()+amount).build());
            return true;
    }

}
