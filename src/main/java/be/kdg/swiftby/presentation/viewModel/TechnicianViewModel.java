package be.kdg.swiftby.presentation.viewModel;

import be.kdg.swiftby.domain.testEnv.Facility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicianViewModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long facilityId;
}
