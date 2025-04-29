package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    List<Technician> findAllByFacility(Facility facility);
    @Query("""
    select te from Technician te
    left join fetch te.facility f
    where te.id = :id
    and f = :facility
""")
    Optional<Technician> findByFacilityAndId(Facility facility, Long id);
    Optional<Technician> findByFacilityIdAndId(Long facilityId, Long id);

    Optional<Technician> findByEmail(String email);
    void deleteAllByFacilityId(Long id);
    boolean existsByEmail(String email);

}
