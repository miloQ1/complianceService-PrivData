package cl.privdata.complianceService.DTO.request;

public class ProcessingPurposeStatusUpdateRequestDTO {

    private boolean active;

    public ProcessingPurposeStatusUpdateRequestDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}