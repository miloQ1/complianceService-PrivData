package cl.privdata.complianceService.DTO.response;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.privdata.complianceService.model.enums.SecurityMeasureType;

public class SecurityMeasureResponseDTO {

    private UUID id;
    private UUID processingActivityId;
    private String name;
    private String description;
    private SecurityMeasureType measureType;
    private boolean implemented;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SecurityMeasureResponseDTO() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }	

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SecurityMeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(SecurityMeasureType measureType) {
        this.measureType = measureType;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
