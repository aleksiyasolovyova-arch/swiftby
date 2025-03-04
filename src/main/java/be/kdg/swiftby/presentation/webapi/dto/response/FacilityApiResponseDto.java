package be.kdg.swiftby.presentation.webapi.dto.response;

public record FacilityApiResponseDto(String name, String email,
                                   String country, String city, String zipCode, String street, String streetNumber, String addressExtra) {
}
