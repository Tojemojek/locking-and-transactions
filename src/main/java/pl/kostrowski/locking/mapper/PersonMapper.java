package pl.kostrowski.locking.mapper;

import org.mapstruct.Mapper;
import pl.kostrowski.locking.dto.PersonDto;
import pl.kostrowski.locking.model.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    List<PersonDto> map(List<Person> personList);
    PersonDto map(Person person);

}
