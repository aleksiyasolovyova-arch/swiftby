package be.kdg.swiftby.presentation.webapi.dto.request;

public record BikeOwnerRequestDto(
        String email,
        String firstName,
        String lastName,
        String phoneNumber
) {}
