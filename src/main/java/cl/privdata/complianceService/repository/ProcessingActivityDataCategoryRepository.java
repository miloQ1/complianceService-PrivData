package cl.privdata.complianceService.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.privdata.complianceService.model.ProcessingActivityDataCategory;

public interface ProcessingActivityDataCategoryRepository extends JpaRepository<ProcessingActivityDataCategory, UUID> {

    Optional<ProcessingActivityDataCategory> findByProcessingActivityIdAndPersonalDataCategoryId(
            UUID processingActivityId,
            UUID personalDataCategoryId
    );

    List<ProcessingActivityDataCategory> findByProcessingActivityId(UUID processingActivityId);

    List<ProcessingActivityDataCategory> findByPersonalDataCategoryId(UUID personalDataCategoryId);

    void deleteByProcessingActivityId(UUID processingActivityId);
}