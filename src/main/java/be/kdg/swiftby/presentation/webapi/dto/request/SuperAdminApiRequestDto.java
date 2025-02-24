package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;

public record SuperAdminApiRequestDto(Long id, String email, String password, String firstName, String lastName, String phoneNumber) {

}
