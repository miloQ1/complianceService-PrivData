package cl.privdata.complianceService.DTO.request;

public class SecurityMeasureImplementedUpdateRequestDTO {

    private boolean implemented;

    public SecurityMeasureImplementedUpdateRequestDTO() {
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }
}