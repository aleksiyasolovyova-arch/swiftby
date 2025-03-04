package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.service.dto.TestBenchDataDto;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class TestBenchData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double rollerTorque;
    private double loadCell;
    private double rol;
    private int loadPower;
    private boolean statusPlug;
    @ManyToOne
    @JoinColumn(name = "test_bench_id")
    private TestBench testBench;

    public TestBenchData() {
    }

    public TestBenchData(TestBenchDataDto dto) {
        this.rollerTorque = dto.rollerTorque();
        this.loadCell = dto.loadCell();
        this.rol = dto.rol();
        this.loadPower = dto.loadPower();
        this.statusPlug = dto.statusPlug();
    }
}
