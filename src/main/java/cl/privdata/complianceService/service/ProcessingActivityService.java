package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.ProcessingActivityCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingActivityStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingActivityUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.ProcessingActivityResponseDTO;
import cl.privdata.complianceService.model.ProcessingActivity;
import cl.privdata.complianceService.model.enums.ProcessingActivityRiskLevel;
import cl.privdata.complianceService.model.enums.ProcessingActivityStatus;
import cl.privdata.complianceService.repository.ProcessingActivityRepository;
import jakarta.transaction.Transactional;

@Service
public class ProcessingActivityService {

    private final ProcessingActivityRepository processingActivityRepository;

    public ProcessingActivityService(ProcessingActivityRepository processingActivityRepository) {
        this.processingActivityRepository = processingActivityRepository;
    }

    public List<ProcessingActivityResponseDTO> getAll(
            UUID organizationId,
            ProcessingActivityStatus status,
            ProcessingActivityRiskLevel riskLevel,
            UUID purposeId,
            String search
    ) {
        List<ProcessingActivity> activities = processingActivityRepository.search(
                organizationId,
                status,
                riskLevel,
                purposeId,
                normalize(search)
        );

        List<ProcessingActivityResponseDTO> response = new ArrayList<>();
        for (ProcessingActivity activity : activities) {
            response.add(toResponseDTO(activity));
        }

        return response;
    }

    public ProcessingActivityResponseDTO getById(UUID id) {
        ProcessingActivity activity = findOrThrow(id);
        return toResponseDTO(activity);
    }

    @Transactional
    public ProcessingActivityResponseDTO create(ProcessingActivityCreateRequestDTO request) {
        String normalizedName = request.getName().trim();

        processingActivityRepository.findByOrganizationIdAndName(
                request.getOrganizationId(),
                normalizedName
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe una actividad de tratamiento con ese nombre en la organización."
            );
        });

        ProcessingActivity activity = new ProcessingActivity();
        activity.setOrganizationId(request.getOrganizationId());
        activity.setName(normalizedName);
        activity.setDescription(request.getDescription());
        activity.setPurposeId(request.getPurposeId());
        activity.setResponsiblePersonId(request.getResponsiblePersonId());
        activity.setStorageLocation(request.getStorageLocation());
        activity.setRetentionPeriodDays(request.getRetentionPeriodDays());
        activity.setInternationalTransfer(request.isInternationalTransfer());
        activity.setThirdPartySharing(request.isThirdPartySharing());

        if (request.getRiskLevel() != null) {
            activity.setRiskLevel(request.getRiskLevel());
        }

        if (request.getStatus() != null) {
            activity.setStatus(request.getStatus());
        }

        ProcessingActivity saved = processingActivityRepository.save(activity);
        return toResponseDTO(saved);
    }

    @Transactional
    public ProcessingActivityResponseDTO update(UUID id, ProcessingActivityUpdateRequestDTO request) {
        ProcessingActivity activity = findOrThrow(id);
        String normalizedName = request.getName().trim();

        processingActivityRepository.findByOrganizationIdAndName(
                activity.getOrganizationId(),
                normalizedName
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otra actividad de tratamiento con ese nombre en la organización."
                );
            }
        });

        activity.setName(normalizedName);
        activity.setDescription(request.getDescription());
        activity.setPurposeId(request.getPurposeId());
        activity.setResponsiblePersonId(request.getResponsiblePersonId());
        activity.setStorageLocation(request.getStorageLocation());
        activity.setRetentionPeriodDays(request.getRetentionPeriodDays());
        activity.setInternationalTransfer(request.isInternationalTransfer());
        activity.setThirdPartySharing(request.isThirdPartySharing());

        if (request.getRiskLevel() != null) {
            activity.setRiskLevel(request.getRiskLevel());
        }

        ProcessingActivity saved = processingActivityRepository.save(activity);
        return toResponseDTO(saved);
    }

    @Transactional
    public ProcessingActivityResponseDTO updateStatus(UUID id, ProcessingActivityStatusUpdateRequestDTO request) {
        ProcessingActivity activity = findOrThrow(id);
        activity.setStatus(request.getStatus());

        ProcessingActivity saved = processingActivityRepository.save(activity);
        return toResponseDTO(saved);
    }

    private ProcessingActivity findOrThrow(UUID id) {
        return processingActivityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Actividad de tratamiento no encontrada."
                ));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private ProcessingActivityResponseDTO toResponseDTO(ProcessingActivity activity) {
        ProcessingActivityResponseDTO dto = new ProcessingActivityResponseDTO();
        dto.setId(activity.getId());
        dto.setOrganizationId(activity.getOrganizationId());
        dto.setName(activity.getName());
        dto.setDescription(activity.getDescription());
        dto.setPurposeId(activity.getPurposeId());
        dto.setResponsiblePersonId(activity.getResponsiblePersonId());
        dto.setStorageLocation(activity.getStorageLocation());
        dto.setRetentionPeriodDays(activity.getRetentionPeriodDays());
        dto.setInternationalTransfer(activity.isInternationalTransfer());
        dto.setThirdPartySharing(activity.isThirdPartySharing());
        dto.setRiskLevel(activity.getRiskLevel());
        dto.setStatus(activity.getStatus());
        dto.setCreatedAt(activity.getCreatedAt());
        dto.setUpdatedAt(activity.getUpdatedAt());
        return dto;
    }
}
