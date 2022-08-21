package kata.bank.exceptions;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(){
        super("Insufficient balance");
    }
}
