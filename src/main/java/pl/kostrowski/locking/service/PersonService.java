package pl.kostrowski.locking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.kostrowski.locking.dto.PersonDto;
import pl.kostrowski.locking.mapper.PersonMapper;
import pl.kostrowski.locking.model.Person;
import pl.kostrowski.locking.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Transactional
    public List<PersonDto> getAllWithoutNPlusOne(PageRequest pageRequest) {
        personRepository.findAllFirstQuery(pageRequest);
        Page<Person> allSecondQuery = personRepository.findAllSecondQuery(pageRequest);
        return personMapper.map(allSecondQuery.getContent());
    }

    @Transactional
    public List<PersonDto> getAllNPlusOne(PageRequest pageRequest) {
        Page<Person> all = personRepository.findAll(pageRequest);
        return personMapper.map(all.getContent());
    }

    @Transactional
    public List<PersonDto> getAll() {
        List<Person> all = personRepository.findAll();
        return personMapper.map(all);
    }

    @Transactional
    public PersonDto getSingle(String bid) {
        Optional<Person> byBid = personRepository.findByBid(bid);
        return byBid.map(personMapper::map).orElseThrow(() -> new IllegalArgumentException("Person with bid " + bid + " does not exist"));
    }
}
