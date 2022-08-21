package kata.bank.controller.dto;

import lombok.Getter;

@Getter
public class OperationDTO {
    private String accountNumber;
    private double amount;
}
