package fca.sprint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fca.sprint.models.TransactionLock;

public interface TransactionLockRepository extends JpaRepository<TransactionLock, Integer> {
}
