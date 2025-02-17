package be.kdg.swiftby.domain.users;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Technician extends Employee{
    private Administrator administrator;
}
