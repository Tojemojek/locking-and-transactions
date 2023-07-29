package pl.kostrowski.nplusone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import pl.kostrowski.nplusone.model.Address;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("Select a from Address a where a.bid = :bid")
    Optional<Address> findByBidRead(String bid);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select a from Address a where a.bid = :bid")
    Optional<Address> findByBidWrite(String bid);
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("Select a from Address a where a.bid = :bid")
    Optional<Address> findByBidForceIncrement(String bid);
    Optional<Address> findByBid(String bid);

}
