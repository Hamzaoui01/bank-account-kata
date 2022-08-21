package kata.bank.exceptions;

public class InvalidOperationTypeCodeException extends RuntimeException{
    public InvalidOperationTypeCodeException(char code){
        super("Invalid Operation Type code : "+ code);
    }
}
