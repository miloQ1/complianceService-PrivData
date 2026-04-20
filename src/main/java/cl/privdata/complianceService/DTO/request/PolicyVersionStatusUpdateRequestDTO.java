package cl.privdata.complianceService.DTO.request;

public class PolicyVersionStatusUpdateRequestDTO {

    private boolean active;

    public PolicyVersionStatusUpdateRequestDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}