package pl.kostrowski.nplusone.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.time.ZonedDateTime;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String bid;
    private String city;
    private String street;
    private Long streetNo;

    @CreatedDate
    private ZonedDateTime createdDate;

    @LastModifiedDate
    private ZonedDateTime lastModified;

    @Version
    private Long version;
}
