package be.kdg.swiftby.domain.users;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemAdmin extends SBUser {
    private Set<Administrator> administrators;
}
