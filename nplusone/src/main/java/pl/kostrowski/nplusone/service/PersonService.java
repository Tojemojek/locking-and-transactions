package pl.kostrowski.nplusone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kostrowski.nplusone.model.Person;
import pl.kostrowski.nplusone.repository.PersonRepository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public List<Person> getAllWithoutNPlusOne(PageRequest pageRequest) {
        personRepository.findAllFirstQuery(pageRequest);
        Page<Person> allSecondQuery = personRepository.findAllSecondQuery(pageRequest);
        return allSecondQuery.getContent();
    }

    @Transactional
    public List<Person> getAllNPlusOne(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest).getContent();
    }

    @Transactional
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Transactional
    public List<Person> getAllInSet() {
        return personRepository.findAllInOneDoesNotWorkQuery();
    }

    @Transactional
    public Person getSingle(String bid) {
        Optional<Person> byBid = personRepository.findByBid(bid);
        return byBid.orElseThrow(() -> new IllegalArgumentException("Person with bid " + bid + " does not exist"));
    }
}
