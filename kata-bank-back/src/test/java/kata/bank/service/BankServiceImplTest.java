package kata.bank.service;

import kata.bank.KataBankApplication;
import kata.bank.entity.Account;
import kata.bank.entity.Operation;
import kata.bank.repository.AccountRepository;
import kata.bank.repository.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    @BeforeEach
    void init(){
        accountRepository.save(Account.builder().number("001").balance(0).build());
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
        int initialSize = operationRepository.findAll().size();
        bankService.deposit("001",200);
        List<Operation> operations = operationRepository.findAll();
        assertThat(operations.size()-initialSize).isEqualTo(1);
        assertThat(operationRepository.findAll().get(initialSize).getType()).isEqualTo(DEBIT.getCode());
    }

    @Test
    void Should_Throw_Exception_When_Deposit_In_Unfounded_Account(){
        Exception exception = assertThrows(RuntimeException.class, () -> bankService.deposit("002",200));
        String expectedMessage = "Requested Account not found with the number : 002";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
