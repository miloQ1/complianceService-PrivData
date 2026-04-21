package cl.privdata.complianceService.DTO.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProcessingActivityDataCategoryResponseDTO {

    private UUID id;
    private UUID processingActivityId;
    private UUID personalDataCategoryId;
    private LocalDateTime createdAt;

    public ProcessingActivityDataCategoryResponseDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProcessingActivityId() {
        return processingActivityId;
    }

    public void setProcessingActivityId(UUID processingActivityId) {
        this.processingActivityId = processingActivityId;
    }

    public UUID getPersonalDataCategoryId() {
        return personalDataCategoryId;
    }

    public void setPersonalDataCategoryId(UUID personalDataCategoryId) {
        this.personalDataCategoryId = personalDataCategoryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}