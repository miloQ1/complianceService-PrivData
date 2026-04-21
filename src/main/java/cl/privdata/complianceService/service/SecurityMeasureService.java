package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.SecurityMeasureCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.SecurityMeasureImplementedUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.SecurityMeasureUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.SecurityMeasureResponseDTO;
import cl.privdata.complianceService.model.SecurityMeasure;
import cl.privdata.complianceService.model.enums.SecurityMeasureType;
import cl.privdata.complianceService.repository.SecurityMeasureRepository;
import jakarta.transaction.Transactional;

@Service
public class SecurityMeasureService {

    private final SecurityMeasureRepository securityMeasureRepository;

    public SecurityMeasureService(SecurityMeasureRepository securityMeasureRepository) {
        this.securityMeasureRepository = securityMeasureRepository;
    }

    public List<SecurityMeasureResponseDTO> getAll(
            UUID processingActivityId,
            Boolean implemented,
            SecurityMeasureType measureType,
            String search
    ) {
        List<SecurityMeasure> measures = securityMeasureRepository.search(
                processingActivityId,
                implemented,
                measureType,
                normalize(search)
        );

        List<SecurityMeasureResponseDTO> response = new ArrayList<>();
        for (SecurityMeasure measure : measures) {
            response.add(toResponseDTO(measure));
        }

        return response;
    }

    public SecurityMeasureResponseDTO getById(UUID id) {
        SecurityMeasure measure = findOrThrow(id);
        return toResponseDTO(measure);
    }

    @Transactional
    public SecurityMeasureResponseDTO create(SecurityMeasureCreateRequestDTO request) {
        String normalizedName = request.getName().trim();

        securityMeasureRepository.findByProcessingActivityIdAndName(
                request.getProcessingActivityId(),
                normalizedName
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe una medida de seguridad con ese nombre para la actividad indicada."
            );
        });

        SecurityMeasure measure = new SecurityMeasure();
        measure.setProcessingActivityId(request.getProcessingActivityId());
        measure.setName(normalizedName);
        measure.setDescription(request.getDescription());
        measure.setMeasureType(request.getMeasureType());
        measure.setImplemented(request.isImplemented());

        SecurityMeasure saved = securityMeasureRepository.save(measure);
        return toResponseDTO(saved);
    }

    @Transactional
    public SecurityMeasureResponseDTO update(UUID id, SecurityMeasureUpdateRequestDTO request) {
        SecurityMeasure measure = findOrThrow(id);
        String normalizedName = request.getName().trim();

        securityMeasureRepository.findByProcessingActivityIdAndName(
                measure.getProcessingActivityId(),
                normalizedName
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otra medida de seguridad con ese nombre para la actividad indicada."
                );
            }
        });

        measure.setName(normalizedName);
        measure.setDescription(request.getDescription());
        measure.setMeasureType(request.getMeasureType());
        measure.setImplemented(request.isImplemented());

        SecurityMeasure saved = securityMeasureRepository.save(measure);
        return toResponseDTO(saved);
    }

    @Transactional
    public SecurityMeasureResponseDTO updateImplemented(UUID id, SecurityMeasureImplementedUpdateRequestDTO request) {
        SecurityMeasure measure = findOrThrow(id);
        measure.setImplemented(request.isImplemented());

        SecurityMeasure saved = securityMeasureRepository.save(measure);
        return toResponseDTO(saved);
    }

    private SecurityMeasure findOrThrow(UUID id) {
        return securityMeasureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medida de seguridad no encontrada."
                ));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private SecurityMeasureResponseDTO toResponseDTO(SecurityMeasure measure) {
        SecurityMeasureResponseDTO dto = new SecurityMeasureResponseDTO();
        dto.setId(measure.getId());
        dto.setProcessingActivityId(measure.getProcessingActivityId());
        dto.setName(measure.getName());
        dto.setDescription(measure.getDescription());
        dto.setMeasureType(measure.getMeasureType());
        dto.setImplemented(measure.isImplemented());
        dto.setCreatedAt(measure.getCreatedAt());
        dto.setUpdatedAt(measure.getUpdatedAt());
        return dto;
    }
}
