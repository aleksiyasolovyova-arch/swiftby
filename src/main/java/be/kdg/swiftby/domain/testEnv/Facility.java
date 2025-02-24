package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull private String name;
    @NonNull private String email;
    @NonNull private String country;
    @NonNull private String city;
    @NonNull private String zipCode;
    @NonNull private String street;
    @NonNull private String streetNumber;
    @NonNull private String addressExtra;
    @OneToMany(mappedBy = "facility")
    private Set<Employee> employees;
}
