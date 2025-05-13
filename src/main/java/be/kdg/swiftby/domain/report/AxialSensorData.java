package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.service.dto.AxialSensorDataDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
    @Entity
    @Data
    public class AxialSensorData {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private double horizontalInclination;
        private double verticalInclination;

        public AxialSensorData() {
        }

        public AxialSensorData(AxialSensorDataDto dto) {
            this.horizontalInclination = dto.horizontalInclination();
            this.verticalInclination = dto.verticalInclination();
        }
    }
