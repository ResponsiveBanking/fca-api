package fca.sprint.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import fca.sprint.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
  Optional<Transaction> findById(String transactionId);

  @Query("select t from Transaction t GROUP BY t.created")
  List<List<Transaction>> groupByDate();
}

