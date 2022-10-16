package pl.kostrowski.locking.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
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
