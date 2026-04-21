package cl.privdata.complianceService.DTO.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class ProcessingActivityDataCategoryReplaceRequestDTO {

    @NotNull
    private List<UUID> personalDataCategoryIds = new ArrayList<>();

    public ProcessingActivityDataCategoryReplaceRequestDTO() {
    }

    public List<UUID> getPersonalDataCategoryIds() {
        return personalDataCategoryIds;
    }

    public void setPersonalDataCategoryIds(List<UUID> personalDataCategoryIds) {
        this.personalDataCategoryIds = personalDataCategoryIds;
    }
}