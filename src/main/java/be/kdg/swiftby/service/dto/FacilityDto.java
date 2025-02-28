package be.kdg.swiftby.service.dto;

public record FacilityDto(Long id, String name, String email,
                          String country, String city, String zipCode, String street, String streetNumber, String addressExtra) {
}
