package pl.kostrowski.locking.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String bid;
    private String city;
    private String street;
    private Long streetNo;

}
