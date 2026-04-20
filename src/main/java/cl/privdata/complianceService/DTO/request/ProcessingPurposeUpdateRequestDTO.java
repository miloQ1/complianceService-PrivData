package cl.privdata.complianceService.DTO.request;

import cl.privdata.complianceService.model.enums.LegalBasis;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProcessingPurposeUpdateRequestDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LegalBasis legalBasis;

    private boolean required;

    public ProcessingPurposeUpdateRequestDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public LegalBasis getLegalBasis() {
        return legalBasis;
    }

    public void setLegalBasis(LegalBasis legalBasis) {
        this.legalBasis = legalBasis;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}