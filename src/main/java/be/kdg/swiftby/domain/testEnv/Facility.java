package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    @OneToMany(mappedBy = "facility")
    private Set<Employee> employees;
}
