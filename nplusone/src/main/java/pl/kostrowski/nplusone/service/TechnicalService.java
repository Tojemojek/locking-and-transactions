package pl.kostrowski.nplusone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kostrowski.nplusone.model.Address;
import pl.kostrowski.nplusone.model.Person;
import pl.kostrowski.nplusone.repository.PersonRepository;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechnicalService {

    private final PersonRepository personRepository;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Transactional
    public List<Person> getAll() {
        List<Person> all = personRepository.findAll();
        all.forEach(Person::getPrimaryAddress);
        all.forEach(a -> a.getAddresses().forEach(Address::getBid));
        return all;
    }

}
