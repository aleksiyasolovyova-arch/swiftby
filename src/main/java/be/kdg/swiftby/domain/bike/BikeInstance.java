package be.kdg.swiftby.domain.bike;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@NoArgsConstructor
public class BikeInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String chassisNumber;

    @ManyToOne(optional = false)
    private BikeModel model;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private Set<BikeOwnership> ownerships;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private Set<BikeReport> reports;

    @OneToMany(mappedBy = "bikeInstance",fetch = FetchType.LAZY)
    private Set<BikeReportSummary> summaries;

    public BikeInstance(String chassisNumber, BikeModel model) {
        this.chassisNumber = chassisNumber;
        this.model = model;
        this.ownerships = new HashSet<>();
    }
}

