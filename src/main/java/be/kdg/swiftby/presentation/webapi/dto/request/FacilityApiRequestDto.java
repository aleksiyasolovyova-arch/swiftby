package be.kdg.swiftby.presentation.webapi.dto.request;

public record FacilityApiRequestDto(String name, String email,
                                    String country, String city, String zipCode, String street, String streetNumber, String addressExtra) {
}
