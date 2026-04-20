package cl.privdata.complianceService.DTO.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class PolicyVersionUpdateRequestDTO {

    @NotBlank
    private String code;

    @NotBlank
    private String title;

    @NotBlank
    private String contentSnapshot;

    @NotBlank
    private String versionLabel;

    private LocalDateTime effectiveFrom;

    public PolicyVersionUpdateRequestDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }	

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }	

    public String getContentSnapshot() {
        return contentSnapshot;
    }

    public void setContentSnapshot(String contentSnapshot) {
        this.contentSnapshot = contentSnapshot;
    }

    public String getVersionLabel() {
        return versionLabel;
    }

    public void setVersionLabel(String versionLabel) {
        this.versionLabel = versionLabel;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
}