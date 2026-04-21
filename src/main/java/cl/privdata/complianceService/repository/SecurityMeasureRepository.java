package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privdata.complianceService.model.SecurityMeasure;
import cl.privdata.complianceService.model.enums.SecurityMeasureType;

public interface SecurityMeasureRepository extends JpaRepository<SecurityMeasure, UUID> {

    Optional<SecurityMeasure> findByProcessingActivityIdAndName(UUID processingActivityId, String name);

    @Query("""
            SELECT sm
            FROM SecurityMeasure sm
            WHERE (:processingActivityId IS NULL OR sm.processingActivityId = :processingActivityId)
              AND (:implemented IS NULL OR sm.implemented = :implemented)
              AND (:measureType IS NULL OR sm.measureType = :measureType)
              AND (
                    :search IS NULL
                    OR LOWER(sm.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(sm.description, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            ORDER BY sm.createdAt DESC
            """)
    List<SecurityMeasure> search(
            UUID processingActivityId,
            Boolean implemented,
            SecurityMeasureType measureType,
            String search
    );
}