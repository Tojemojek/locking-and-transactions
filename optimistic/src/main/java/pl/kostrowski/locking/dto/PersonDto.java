package pl.kostrowski.locking.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonDto {

    private String bid;
    private String name;
    private List<AccountDto> accounts;
    private AddressDto primaryAddress;
    private List<AddressDto> addresses;

}
