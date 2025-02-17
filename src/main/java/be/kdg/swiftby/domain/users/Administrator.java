package be.kdg.swiftby.domain.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Administrator extends Employee{
    private Set<Technician> technicians;
    private Set<Customer> customers;
    private SystemAdmin systemAdmin;
}
