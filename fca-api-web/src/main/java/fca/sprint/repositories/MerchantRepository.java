package fca.sprint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fca.sprint.models.Merchant;
import fca.sprint.models.Transaction;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
}
