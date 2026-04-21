package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privdata.complianceService.model.PersonalDataCategory;

public interface PersonalDataCategoryRepository extends JpaRepository<PersonalDataCategory, UUID> {

    Optional<PersonalDataCategory> findByOrganizationIdAndName(UUID organizationId, String name);

    @Query("""
            SELECT pdc
            FROM PersonalDataCategory pdc
            WHERE (:organizationId IS NULL OR pdc.organizationId = :organizationId)
              AND (:active IS NULL OR pdc.active = :active)
              AND (:sensitive IS NULL OR pdc.sensitive = :sensitive)
              AND (
                    :search IS NULL
                    OR LOWER(pdc.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(pdc.description, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            ORDER BY pdc.createdAt DESC
            """)
    List<PersonalDataCategory> search(UUID organizationId, Boolean active, Boolean sensitive, String search);
}