package kata.bank.controller;

import kata.bank.controller.dto.OperationDTO;
import kata.bank.repository.AccountRepository;
import kata.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class BankController {

    private final BankService bankService;
    private final AccountRepository accountRepository;

    @GetMapping("accounts/{number}")
    public ResponseEntity getAccount(@PathVariable String number){
        try{
            return new ResponseEntity<>(bankService.getAccount(number),HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("accounts")
    public ResponseEntity getAccounts(){
        try{
            return new ResponseEntity<>(accountRepository.findAll(),HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("operations/debit")
    public ResponseEntity debitAccount(@RequestBody OperationDTO operationDTO){
        if (operationDTO.getAmount()<=0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity<>(bankService.deposit(operationDTO.getAccountNumber(), operationDTO.getAmount()),HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("operations/credit")
    public ResponseEntity creditAccount(@RequestBody OperationDTO operationDTO){
        if (operationDTO.getAmount()<=0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity<>(bankService.withDraw(operationDTO.getAccountNumber(), operationDTO.getAmount()),HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("operations/account/{number}")
    public ResponseEntity getOperations(@PathVariable String number){
        try{
            return new ResponseEntity<>(bankService.getAccount(number),HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
