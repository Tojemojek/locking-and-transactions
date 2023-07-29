package pl.kostrowski.pessimistic.model.audited;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByBid(String bid);
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    @Query("select a from Account a where a.bid = :bid")
    Optional<Account> findByBidPessimisticRead(String bid);
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.bid = :bid")
    Optional<Account> findByBidPessimisticWrite(String bid);
    @Lock(value = LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("select a from Account a where a.bid = :bid")
    Optional<Account> findByBidPessimisticForce(String bid);


}
