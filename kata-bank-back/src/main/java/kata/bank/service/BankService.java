package kata.bank.service;

import kata.bank.entity.Account;

public interface BankService {
    Account getAccount(String accountNumber);
    boolean deposit(String accountNumber,double amount);
}
