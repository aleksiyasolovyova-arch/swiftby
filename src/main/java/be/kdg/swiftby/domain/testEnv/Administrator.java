package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Administrator extends Employee{
    @OneToMany(mappedBy = "administrator")
    private Set<Technician> technicians;
    // TODO: CREATE AN INTERMEDIATE CLASS FOR THIS AND NOT USE MANY_TO_MANY ANNOTATION
    @ManyToMany
    @JoinTable(
            name = "admin_customer",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;
    @ManyToOne
    private SystemAdmin systemAdmin;
}
