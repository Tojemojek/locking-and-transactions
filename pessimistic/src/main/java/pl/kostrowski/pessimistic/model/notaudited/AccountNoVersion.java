package pl.kostrowski.pessimistic.model.notaudited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class AccountNoVersion {
    @Id
    @GeneratedValue
    private Long id;
    private String bid;
    private BigDecimal amount;
}
