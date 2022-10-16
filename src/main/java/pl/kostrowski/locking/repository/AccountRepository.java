package pl.kostrowski.locking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.locking.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByBid(String bid);
}
