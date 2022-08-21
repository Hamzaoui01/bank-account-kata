package kata.bank.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class OperationTypeTest {

    @ParameterizedTest
    @CsvSource({"c,CREDIT","C,CREDIT","d,DEBIT","D,DEBIT"})
    void Should_Return_OperationType_When_Give_Code(String code, String expectedType){
        OperationType actualType = OperationType.getTypeByCode(code.charAt(0));
        assertThat(actualType.name()).isEqualTo(expectedType);
    }

    @Test
    void Should_Throw_Exception_When_Code_Invalid(){
        Exception exception = assertThrows(RuntimeException.class, () -> OperationType.getTypeByCode('y'));
        String expectedMessage = "Invalid Operation Type code : y";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}
