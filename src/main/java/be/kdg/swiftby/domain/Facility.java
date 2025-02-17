package be.kdg.swiftby.domain;

import lombok.Data;

@Data
public class Facility {
    private Long id;
    private String name;
    private String address;
    private String email;
}
