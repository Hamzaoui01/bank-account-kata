package kata.bank.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountNumber){
        super("Requested Account not found with the number : "+accountNumber);
    }
}
