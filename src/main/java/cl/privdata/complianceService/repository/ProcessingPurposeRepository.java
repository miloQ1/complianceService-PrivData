package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privdata.complianceService.model.ProcessingPurpose;
import cl.privdata.complianceService.model.enums.LegalBasis;

public interface ProcessingPurposeRepository extends JpaRepository<ProcessingPurpose, UUID> {

    Optional<ProcessingPurpose> findByOrganizationIdAndCode(UUID organizationId, String code);

    @Query("""
            SELECT pp
            FROM ProcessingPurpose pp
            WHERE (:organizationId IS NULL OR pp.organizationId = :organizationId)
              AND (:active IS NULL OR pp.active = :active)
              AND (:legalBasis IS NULL OR pp.legalBasis = :legalBasis)
              AND (
                    :search IS NULL
                    OR LOWER(pp.code) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(pp.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(pp.description, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            ORDER BY pp.createdAt DESC
            """)
    List<ProcessingPurpose> search(UUID organizationId, Boolean active, LegalBasis legalBasis, String search);
}