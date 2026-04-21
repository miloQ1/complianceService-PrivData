package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privdata.complianceService.model.ProcessingActivity;
import cl.privdata.complianceService.model.enums.ProcessingActivityRiskLevel;
import cl.privdata.complianceService.model.enums.ProcessingActivityStatus;

public interface ProcessingActivityRepository extends JpaRepository<ProcessingActivity, UUID> {

    Optional<ProcessingActivity> findByOrganizationIdAndName(UUID organizationId, String name);

    @Query("""
            SELECT pa
            FROM ProcessingActivity pa
            WHERE (:organizationId IS NULL OR pa.organizationId = :organizationId)
              AND (:status IS NULL OR pa.status = :status)
              AND (:riskLevel IS NULL OR pa.riskLevel = :riskLevel)
              AND (:purposeId IS NULL OR pa.purposeId = :purposeId)
              AND (
                    :search IS NULL
                    OR LOWER(pa.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(pa.description, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(pa.storageLocation, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            ORDER BY pa.createdAt DESC
            """)
    List<ProcessingActivity> search(
            UUID organizationId,
            ProcessingActivityStatus status,
            ProcessingActivityRiskLevel riskLevel,
            UUID purposeId,
            String search
    );
}
