package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.TestBench;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestBenchRepository extends JpaRepository<TestBench, Long> {
    List<TestBench> findAllByFacility(Facility facility);

    @Query("""
    select tb from TestBench tb
    left join tb.facility f
    where tb.id = :id
    and f = :facility
""")
    Optional<TestBench> findByFacilityAndId(Facility facility, Long id);
    void removeAllByFacility(Facility facility);



}


