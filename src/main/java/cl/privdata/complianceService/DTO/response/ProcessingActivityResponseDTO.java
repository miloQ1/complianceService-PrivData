package cl.privdata.complianceService.DTO.response;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.privdata.complianceService.model.enums.ProcessingActivityRiskLevel;
import cl.privdata.complianceService.model.enums.ProcessingActivityStatus;

public class ProcessingActivityResponseDTO {

    private UUID id;
    private UUID organizationId;
    private String name;
    private String description;
    private UUID purposeId;
    private UUID responsiblePersonId;
    private String storageLocation;
    private Integer retentionPeriodDays;
    private boolean internationalTransfer;
    private boolean thirdPartySharing;
    private ProcessingActivityRiskLevel riskLevel;
    private ProcessingActivityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProcessingActivityResponseDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
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

    public UUID getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(UUID purposeId) {
        this.purposeId = purposeId;
    }

    public UUID getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(UUID responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Integer getRetentionPeriodDays() {
        return retentionPeriodDays;
    }

    public void setRetentionPeriodDays(Integer retentionPeriodDays) {
        this.retentionPeriodDays = retentionPeriodDays;
    }

    public boolean isInternationalTransfer() {
        return internationalTransfer;
    }

    public void setInternationalTransfer(boolean internationalTransfer) {
        this.internationalTransfer = internationalTransfer;
    }

    public boolean isThirdPartySharing() {
        return thirdPartySharing;
    }

    public void setThirdPartySharing(boolean thirdPartySharing) {
        this.thirdPartySharing = thirdPartySharing;
    }

    public ProcessingActivityRiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(ProcessingActivityRiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public ProcessingActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessingActivityStatus status) {
        this.status = status;
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