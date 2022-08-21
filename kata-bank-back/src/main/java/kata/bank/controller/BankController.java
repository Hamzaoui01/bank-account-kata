package kata.bank.controller;

import kata.bank.controller.dto.OperationDTO;
import kata.bank.entity.Account;
import kata.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("accounts/{number}")
    public ResponseEntity<Account> getAccount(@PathVariable String number){
        try{
            return new ResponseEntity<>(bankService.getAccount(number),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("operations/debit")
    public ResponseEntity<Boolean> debitAccount(@RequestBody OperationDTO operationDTO){
        if (operationDTO.getAmount()<=0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity<>(bankService.deposit(operationDTO.getAccountNumber(), operationDTO.getAmount()),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
