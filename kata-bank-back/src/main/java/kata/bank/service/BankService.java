package kata.bank.service;

import kata.bank.entity.Account;

public interface BankService {
    Account getAccount(String accountNumber);
    Account deposit(String accountNumber, double amount);
    Account withDraw(String accountNumber, double amount);
}
