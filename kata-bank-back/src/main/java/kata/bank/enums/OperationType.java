package kata.bank.enums;

import kata.bank.exceptions.InvalidOperationTypeCodeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.Character.toUpperCase;
import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Getter
public enum OperationType {

    DEBIT('D'), CREDIT('C');
    private final char code;

    public static OperationType getTypeByCode(char code){
       return stream(OperationType.values())
               .filter(e->e.getCode()== toUpperCase(code))
               .findFirst()
               .orElseThrow(() -> new InvalidOperationTypeCodeException(code));
    }
}
