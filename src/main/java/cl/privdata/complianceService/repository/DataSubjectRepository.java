package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privdata.complianceService.model.DataSubject;
import cl.privdata.complianceService.model.enums.DataSubjectStatus;

public interface DataSubjectRepository extends JpaRepository<DataSubject, UUID> {

    Optional<DataSubject> findByOrganizationIdAndDocumentNumber(UUID organizationId, String documentNumber);

    @Query("""
            SELECT ds
            FROM DataSubject ds
            WHERE (:organizationId IS NULL OR ds.organizationId = :organizationId)
              AND (:status IS NULL OR ds.status = :status)
              AND (
                    :search IS NULL
                    OR LOWER(ds.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(ds.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(ds.documentNumber) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(COALESCE(ds.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            ORDER BY ds.createdAt DESC
            """)
    List<DataSubject> search(UUID organizationId, DataSubjectStatus status, String search);
}