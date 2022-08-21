package kata.bank.service;

import kata.bank.KataBankApplication;
import kata.bank.entity.Account;
import kata.bank.entity.Operation;
import kata.bank.exceptions.InsufficientBalanceException;
import kata.bank.repository.AccountRepository;
import kata.bank.repository.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static kata.bank.enums.OperationType.CREDIT;
import static kata.bank.enums.OperationType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KataBankApplication.class)
class BankServiceImplTest {

    @Autowired
    BankServiceImpl bankService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationRepository operationRepository;

    static int INITIAL_BALANCE = 90000;

    @BeforeEach
    void init(){
        accountRepository.save(Account.builder().number("001").build());
        accountRepository.save(Account.builder().number("003").balance(INITIAL_BALANCE).build());
        accountRepository.save(Account.builder().number("004").build());
    }

    @Test
    void Should_Increase_Balance_When_Deposit_Is_Made(){
        bankService.deposit("001",200);
        Account currentAccount = accountRepository.findById("001").orElse(null);
        assertThat(currentAccount).isNotNull();
        assertThat(currentAccount.getBalance()).isEqualTo(200);
    }

    @Test
    void Should_Register_Debit_Operation_When_Deposit_Is_Made(){
        String accountNumber = "001";
        Account account = bankService.getAccount(accountNumber);
        int initialSize = operationRepository.findByAccount(account).size();
        bankService.deposit(accountNumber,200);
        List<Operation> operations = operationRepository.findByAccount(account);
        assertThat(operations.size()-initialSize).isEqualTo(1);
        assertThat(operations.get(initialSize).getType()).isEqualTo(DEBIT.getCode());
    }

    @Test
    void Should_Throw_Exception_When_Deposit_In_Unfounded_Account(){
        Exception exception = assertThrows(RuntimeException.class, () -> bankService.deposit("002",200));
        String expectedMessage = "Requested Account not found with the number : 002";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void Should_Decrease_Balance_When_Withdraw_Is_Made(){
        int amount = 1000;
        String accountNumber = "003";
        bankService.withDraw(accountNumber, amount);
        Account currentAccount = accountRepository.findById(accountNumber).orElse(null);
        assertThat(currentAccount).isNotNull();
        assertThat(currentAccount.getBalance()).isEqualTo(INITIAL_BALANCE-amount);
    }

    @Test
    void Should_Throw_Exception_When_Withdraw_More_Than_Balance(){
        Exception exception = assertThrows(InsufficientBalanceException.class, () -> bankService.withDraw("003",INITIAL_BALANCE+1));
        String expectedMessage = "Insufficient balance";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void Should_Register_Credit_Operation_When_Deposit_Is_Made(){
        String accountNumber = "003";
        Account account = bankService.getAccount(accountNumber);
        int initialSize = operationRepository.findByAccount(account).size();

        bankService.withDraw(accountNumber,200);
        List<Operation> operations = operationRepository.findByAccount(account);

        assertThat(operations.size()-initialSize).isEqualTo(1);
        assertThat(operations.get(initialSize).getType()).isEqualTo(CREDIT.getCode());
    }

    @Test
    void Should_Retrieve_All_Operations_When_Give_Account_Number(){
        String accountNumber = "004";
        List<Operation> operations = bankService.getOperations(accountNumber);
        assertThat(operations.size()).isZero();
        bankService.deposit(accountNumber,200);
        bankService.deposit(accountNumber,200);
        bankService.withDraw(accountNumber,200);
        operations = bankService.getOperations(accountNumber);
        assertThat(operations.size()).isEqualTo(3);
    }
}
