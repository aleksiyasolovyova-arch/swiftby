package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.domain.testEnv.Facility;

public record TechnicianApiRequestDto(Long facilityId, Long id, String email, String password, String firstName, String lastName, String phoneNumber) {
}
