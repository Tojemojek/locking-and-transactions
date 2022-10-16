package pl.kostrowski.locking.mapper;

import org.mapstruct.Mapper;
import pl.kostrowski.locking.dto.AddressDto;
import pl.kostrowski.locking.model.Address;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    List<AddressDto> map(List<Address> personList);

    AddressDto map(Address person);

}
