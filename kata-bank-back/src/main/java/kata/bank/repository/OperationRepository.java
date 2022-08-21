package kata.bank.repository;

import kata.bank.entity.Account;
import kata.bank.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation,Integer> {
    List<Operation> findByAccount(Account account);
}
