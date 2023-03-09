package pl.kostrowski.locking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private String bid;
    private BigDecimal amount;

}
