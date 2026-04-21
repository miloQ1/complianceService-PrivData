package cl.privdata.complianceService.model;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.privdata.complianceService.model.enums.ProcessingActivityRiskLevel;
import cl.privdata.complianceService.model.enums.ProcessingActivityStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "processing_activities",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_processing_activity_org_name",
                        columnNames = {"organization_id", "name"}
                )
        }
)
public class ProcessingActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "purpose_id", nullable = false)
    private UUID purposeId;

    @Column(name = "responsible_person_id")
    private UUID responsiblePersonId;

    @Column(name = "storage_location", length = 255)
    private String storageLocation;

    @Column(name = "retention_period_days")
    private Integer retentionPeriodDays;

    @Column(name = "international_transfer", nullable = false)
    private boolean internationalTransfer;

    @Column(name = "third_party_sharing", nullable = false)
    private boolean thirdPartySharing;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 30)
    private ProcessingActivityRiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProcessingActivityStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ProcessingActivity() {
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = ProcessingActivityStatus.DRAFT;
        }

        if (this.riskLevel == null) {
            this.riskLevel = ProcessingActivityRiskLevel.MEDIUM;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
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

    public UUID getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(UUID purposeId) {
        this.purposeId = purposeId;
    }

    public UUID getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(UUID responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Integer getRetentionPeriodDays() {
        return retentionPeriodDays;
    }

    public void setRetentionPeriodDays(Integer retentionPeriodDays) {
        this.retentionPeriodDays = retentionPeriodDays;
    }

    public boolean isInternationalTransfer() {
        return internationalTransfer;
    }

    public void setInternationalTransfer(boolean internationalTransfer) {
        this.internationalTransfer = internationalTransfer;
    }

    public boolean isThirdPartySharing() {
        return thirdPartySharing;
    }

    public void setThirdPartySharing(boolean thirdPartySharing) {
        this.thirdPartySharing = thirdPartySharing;
    }

    public ProcessingActivityRiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(ProcessingActivityRiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public ProcessingActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessingActivityStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}