package cl.privdata.complianceService.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "processing_activity_data_categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_processing_activity_data_category",
                        columnNames = {"processing_activity_id", "personal_data_category_id"}
                )
        }
)
public class ProcessingActivityDataCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "processing_activity_id", nullable = false)
    private UUID processingActivityId;

    @Column(name = "personal_data_category_id", nullable = false)
    private UUID personalDataCategoryId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ProcessingActivityDataCategory() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
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

    public UUID getPersonalDataCategoryId() {
        return personalDataCategoryId;
    }

    public void setPersonalDataCategoryId(UUID personalDataCategoryId) {
        this.personalDataCategoryId = personalDataCategoryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}