package cl.privdata.complianceService.DTO.request;

import cl.privdata.complianceService.model.enums.SecurityMeasureType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SecurityMeasureUpdateRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private SecurityMeasureType measureType;

    private boolean implemented;

    public SecurityMeasureUpdateRequestDTO() {
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

    public SecurityMeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(SecurityMeasureType measureType) {
        this.measureType = measureType;
    }

    public boolean isImplemented() {
        return implemented;
    }

    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }
}