package be.kdg.swiftby.domain.bike;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Battery {
    @Id
    // sequence strategy appears to be the best for Postgresql database
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int capacity;

}
