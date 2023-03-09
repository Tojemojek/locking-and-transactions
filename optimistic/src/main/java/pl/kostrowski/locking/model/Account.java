package pl.kostrowski.locking.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String bid;
    private BigDecimal amount;

    @CreatedDate
    private ZonedDateTime createdDate;

    @LastModifiedDate
    private ZonedDateTime lastModified;

    @Version
    private Long version;
}
