package cl.privdata.complianceService.DTO.request;
public class PersonalDataCategoryStatusUpdateRequestDTO {

    private boolean active;

    public PersonalDataCategoryStatusUpdateRequestDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}