package pl.kostrowski.locking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kostrowski.locking.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {

    //This wont work - multiple bags fetching is not allowed
    @EntityGraph(type= EntityGraph.EntityGraphType.FETCH, attributePaths={"accounts", "addresses"} )
    @Query("Select p from Person p")
    List<Person> findAllInOneDoesNotWorkQuery();
    @EntityGraph(type= EntityGraph.EntityGraphType.FETCH, attributePaths={"accounts"} )
    @Query("Select p from Person p")
    Page<Person> findAllFirstQuery(Pageable pageable);

    @EntityGraph(type= EntityGraph.EntityGraphType.FETCH, attributePaths={"addresses"} )
    @Query("Select p from Person p")
    Page<Person> findAllSecondQuery(Pageable pageable);

    Optional<Person> findByBid(String bid);

}
