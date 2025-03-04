package be.kdg.swiftby.presentation.webapi.dto.request;

//2025-02-25: exactly the same as FacilityDto
public record FacilityApiRequestDto(Long id, String name, String email,
                                    String country, String city, String zipCode, String street, String streetNumber, String addressExtra) {
}
