package cl.privdata.complianceService.DTO.request;

import cl.privdata.complianceService.model.enums.DataSubjectStatus;
import jakarta.validation.constraints.NotNull;

public class DataSubjectStatusUpdateRequestDTO {

    @NotNull
    private DataSubjectStatus status;

    public DataSubjectStatusUpdateRequestDTO() {
    }

    public DataSubjectStatus getStatus() {
        return status;
    }

    public void setStatus(DataSubjectStatus status) {
        this.status = status;
    }
}