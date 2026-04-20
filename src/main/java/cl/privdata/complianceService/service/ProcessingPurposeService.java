package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.ProcessingPurposeCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingPurposeStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingPurposeUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.ProcessingPurposeResponseDTO;
import cl.privdata.complianceService.model.ProcessingPurpose;
import cl.privdata.complianceService.model.enums.LegalBasis;
import cl.privdata.complianceService.repository.ProcessingPurposeRepository;
import jakarta.transaction.Transactional;

@Service
public class ProcessingPurposeService {

    private final ProcessingPurposeRepository processingPurposeRepository;

    public ProcessingPurposeService(ProcessingPurposeRepository processingPurposeRepository) {
        this.processingPurposeRepository = processingPurposeRepository;
    }

    public List<ProcessingPurposeResponseDTO> getAll(UUID organizationId, Boolean active, LegalBasis legalBasis, String search) {
        List<ProcessingPurpose> purposes = processingPurposeRepository.search(
                organizationId,
                active,
                legalBasis,
                normalizeSearch(search)
        );

        List<ProcessingPurposeResponseDTO> response = new ArrayList<>();
        for (ProcessingPurpose purpose : purposes) {
            response.add(toResponseDTO(purpose));
        }

        return response;
    }

    public ProcessingPurposeResponseDTO getById(UUID id) {
        ProcessingPurpose purpose = findOrThrow(id);
        return toResponseDTO(purpose);
    }

    @Transactional
    public ProcessingPurposeResponseDTO create(ProcessingPurposeCreateRequestDTO request) {
        processingPurposeRepository.findByOrganizationIdAndCode(
                request.getOrganizationId(),
                request.getCode().trim()
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe una finalidad con ese código en la organización."
            );
        });

        ProcessingPurpose purpose = new ProcessingPurpose();
        purpose.setOrganizationId(request.getOrganizationId());
        purpose.setCode(request.getCode().trim().toUpperCase());
        purpose.setName(request.getName().trim());
        purpose.setDescription(request.getDescription());
        purpose.setLegalBasis(request.getLegalBasis());
        purpose.setRequired(request.isRequired());

        ProcessingPurpose saved = processingPurposeRepository.save(purpose);
        return toResponseDTO(saved);
    }

    @Transactional
    public ProcessingPurposeResponseDTO update(UUID id, ProcessingPurposeUpdateRequestDTO request) {
        ProcessingPurpose purpose = findOrThrow(id);

        processingPurposeRepository.findByOrganizationIdAndCode(
                purpose.getOrganizationId(),
                request.getCode().trim()
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otra finalidad con ese código en la organización."
                );
            }
        });

        purpose.setCode(request.getCode().trim().toUpperCase());
        purpose.setName(request.getName().trim());
        purpose.setDescription(request.getDescription());
        purpose.setLegalBasis(request.getLegalBasis());
        purpose.setRequired(request.isRequired());

        ProcessingPurpose saved = processingPurposeRepository.save(purpose);
        return toResponseDTO(saved);
    }

    @Transactional
    public ProcessingPurposeResponseDTO updateStatus(UUID id, ProcessingPurposeStatusUpdateRequestDTO request) {
        ProcessingPurpose purpose = findOrThrow(id);
        purpose.setActive(request.isActive());

        ProcessingPurpose saved = processingPurposeRepository.save(purpose);
        return toResponseDTO(saved);
    }

    private ProcessingPurpose findOrThrow(UUID id) {
        return processingPurposeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Finalidad de tratamiento no encontrada."
                ));
    }

    private String normalizeSearch(String search) {
        if (search == null || search.isBlank()) {
            return null;
        }
        return search.trim();
    }

    private ProcessingPurposeResponseDTO toResponseDTO(ProcessingPurpose purpose) {
        ProcessingPurposeResponseDTO dto = new ProcessingPurposeResponseDTO();
        dto.setId(purpose.getId());
        dto.setOrganizationId(purpose.getOrganizationId());
        dto.setCode(purpose.getCode());
        dto.setName(purpose.getName());
        dto.setDescription(purpose.getDescription());
        dto.setLegalBasis(purpose.getLegalBasis());
        dto.setRequired(purpose.isRequired());
        dto.setActive(purpose.isActive());
        dto.setCreatedAt(purpose.getCreatedAt());
        dto.setUpdatedAt(purpose.getUpdatedAt());
        return dto;
    }
}