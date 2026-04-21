package cl.privdata.complianceService.DTO.request;

import java.util.UUID;

import cl.privdata.complianceService.model.enums.ProcessingActivityRiskLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProcessingActivityUpdateRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private UUID purposeId;

    private UUID responsiblePersonId;
    private String storageLocation;
    private Integer retentionPeriodDays;
    private boolean internationalTransfer;
    private boolean thirdPartySharing;
    private ProcessingActivityRiskLevel riskLevel;

    public ProcessingActivityUpdateRequestDTO() {
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
}
