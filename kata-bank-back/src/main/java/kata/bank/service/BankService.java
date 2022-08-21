package kata.bank.service;

import kata.bank.entity.Account;
import kata.bank.entity.Operation;

import java.util.List;

public interface BankService {
    Account getAccount(String accountNumber);
    Account deposit(String accountNumber, double amount);
    Account withDraw(String accountNumber, double amount);
    List<Operation> getOperations(String accountNumber);
}
