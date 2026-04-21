package cl.privdata.complianceService.model;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.privdata.complianceService.model.enums.SecurityMeasureType;
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
        name = "security_measures",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_security_measure_activity_name",
                        columnNames = {"processing_activity_id", "name"}
                )
        }
)
public class SecurityMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "processing_activity_id", nullable = false)
    private UUID processingActivityId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "measure_type", nullable = false, length = 30)
    private SecurityMeasureType measureType;

    @Column(nullable = false)
    private boolean implemented;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public SecurityMeasure() {
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
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

    public UUID getProcessingActivityId() {
        return processingActivityId;
    }

    public void setProcessingActivityId(UUID processingActivityId) {
        this.processingActivityId = processingActivityId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
