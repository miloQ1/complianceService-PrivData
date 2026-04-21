package cl.privdata.complianceService.DTO.request;

import jakarta.validation.constraints.NotBlank;

public class PersonalDataCategoryUpdateRequestDTO {

    @NotBlank
    private String name;

    private String description;

    private boolean sensitive;

    public PersonalDataCategoryUpdateRequestDTO() {
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

    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }
}