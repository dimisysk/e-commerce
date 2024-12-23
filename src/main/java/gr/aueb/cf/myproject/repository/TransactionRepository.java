package gr.aueb.cf.myproject.repository;

import gr.aueb.cf.myproject.core.enums.Status;
import gr.aueb.cf.myproject.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findById(Long id);
    Optional<Transaction> findByUserId(Long userId);
    Optional<Transaction> findByStatus(Status status);
}
