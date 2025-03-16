package be.kdg.swiftby.presentation.webapi.dto.response;

public record TechnicianApiResponseDto(Long facilityId, Long id, String email, String password, String firstName, String lastName, String phoneNumber) {
}
