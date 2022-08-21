package kata.bank.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String number;
    private double balance;

    @OneToMany(mappedBy = "account")
    private List<Operation> operations;
}
