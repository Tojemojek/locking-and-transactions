package pl.kostrowski.pessimistic.model.notaudited;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountNoVersionRepository extends JpaRepository<AccountNoVersion, Long> {

    Optional<AccountNoVersion> findByBid(String bid);

}
