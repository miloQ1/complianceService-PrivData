package cl.privdata.complianceService.DTO.request;

import cl.privdata.complianceService.model.enums.ProcessingActivityStatus;
import jakarta.validation.constraints.NotNull;

public class ProcessingActivityStatusUpdateRequestDTO {

    @NotNull
    private ProcessingActivityStatus status;

    public ProcessingActivityStatusUpdateRequestDTO() {
    }

    public ProcessingActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessingActivityStatus status) {
        this.status = status;
    }
}
