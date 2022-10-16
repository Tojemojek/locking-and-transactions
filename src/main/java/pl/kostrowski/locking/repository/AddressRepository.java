package pl.kostrowski.locking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.locking.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByBid(String bid);

}
