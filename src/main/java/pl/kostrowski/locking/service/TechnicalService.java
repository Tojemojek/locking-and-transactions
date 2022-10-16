package pl.kostrowski.locking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kostrowski.locking.model.Account;
import pl.kostrowski.locking.model.Address;
import pl.kostrowski.locking.model.Person;
import pl.kostrowski.locking.repository.PersonRepository;

import javax.transaction.Transactional;
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
        all.forEach(a -> a.getAccounts().forEach(Account::getBid));
        return all;
    }

}
