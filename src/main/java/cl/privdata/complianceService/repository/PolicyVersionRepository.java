package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privdata.complianceService.model.PolicyVersion;

public interface PolicyVersionRepository extends JpaRepository<PolicyVersion, UUID> {

    Optional<PolicyVersion> findByOrganizationIdAndCodeAndVersionLabel(
            UUID organizationId,
            String code,
            String versionLabel
    );

    List<PolicyVersion> findByOrganizationIdAndCode(UUID organizationId, String code);

    @Query("""
            SELECT pv
            FROM PolicyVersion pv
            WHERE (:organizationId IS NULL OR pv.organizationId = :organizationId)
              AND (:active IS NULL OR pv.active = :active)
              AND (:code IS NULL OR LOWER(pv.code) = LOWER(:code))
              AND (
                    :search IS NULL
                    OR LOWER(pv.code) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(pv.title) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(pv.versionLabel) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            ORDER BY pv.createdAt DESC
            """)
    List<PolicyVersion> search(UUID organizationId, Boolean active, String code, String search);
}
