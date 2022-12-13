package kata.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kata.bank.controller.dto.OperationDTO;
import kata.bank.entity.Account;
import kata.bank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;


    @BeforeEach
    void init(){
    }

    private void addAccountToRepository(String num, long initialBalance) {
        accountRepository.save(Account.builder()
                .number(num)
                .balance(initialBalance)
                .build());
    }

    @Test
    void should_Return_Account() throws Exception {
        addAccountToRepository("001",300);
        this.mockMvc.perform(get("/accounts/001")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"number\":\"001\",\"balance\":300.0,\"operations\":[]}")));
    }

    @Test
    void should_Return_Bad_Request_When_No_Account_Found() throws Exception {
        this.mockMvc.perform(get("/accounts/002")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Requested Account not found with the number : 002")));
    }

    @Test
    void should_Deposit_Money() throws Exception {
        String num = "003";
        addAccountToRepository(num,500);
        this.mockMvc.perform(
                post("/operations/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(OperationDTO.builder().accountNumber(num).amount(200.99).build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"number\":\""+num+"\",\"balance\":700.99,\"operations\":null}")));
    }

    //@Test
    void should_Withdraw_Money() throws Exception {
        this.mockMvc.perform(
                post("/operations/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(OperationDTO.builder().accountNumber("001").amount(100).build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"number\":\"001\",\"balance\":500.99,\"operations\":null}")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
