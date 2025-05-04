package be.kdg.swiftby.presentation.webapi.dto.request;

public record EmployeeRequestDto(
        String email, String password, String firstName,
        String lastName, String phoneNumber, Long facilityId
) {

}
