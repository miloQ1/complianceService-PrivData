package cl.privdata.complianceService.DTO.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class ProcessingActivityDataCategoryCreateRequestDTO {

    @NotNull
    private UUID processingActivityId;

    @NotNull
    private UUID personalDataCategoryId;

    public ProcessingActivityDataCategoryCreateRequestDTO() {
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
}