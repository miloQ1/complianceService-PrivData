package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.PolicyVersionCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.PolicyVersionStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.PolicyVersionUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.PolicyVersionResponseDTO;
import cl.privdata.complianceService.model.PolicyVersion;
import cl.privdata.complianceService.repository.PolicyVersionRepository;
import jakarta.transaction.Transactional;

@Service
public class PolicyVersionService {

    private final PolicyVersionRepository policyVersionRepository;

    public PolicyVersionService(PolicyVersionRepository policyVersionRepository) {
        this.policyVersionRepository = policyVersionRepository;
    }

    public List<PolicyVersionResponseDTO> getAll(UUID organizationId, Boolean active, String code, String search) {
        List<PolicyVersion> policyVersions = policyVersionRepository.search(
                organizationId,
                active,
                normalize(code),
                normalize(search)
        );

        List<PolicyVersionResponseDTO> response = new ArrayList<>();
        for (PolicyVersion policyVersion : policyVersions) {
            response.add(toResponseDTO(policyVersion));
        }

        return response;
    }

    public PolicyVersionResponseDTO getById(UUID id) {
        PolicyVersion policyVersion = findOrThrow(id);
        return toResponseDTO(policyVersion);
    }

    @Transactional
    public PolicyVersionResponseDTO create(PolicyVersionCreateRequestDTO request) {
        String normalizedCode = request.getCode().trim().toUpperCase();
        String normalizedVersionLabel = request.getVersionLabel().trim().toUpperCase();

        policyVersionRepository.findByOrganizationIdAndCodeAndVersionLabel(
                request.getOrganizationId(),
                normalizedCode,
                normalizedVersionLabel
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe una versión de política con ese código y versión en la organización."
            );
        });

        PolicyVersion policyVersion = new PolicyVersion();
        policyVersion.setOrganizationId(request.getOrganizationId());
        policyVersion.setCode(normalizedCode);
        policyVersion.setTitle(request.getTitle().trim());
        policyVersion.setContentSnapshot(request.getContentSnapshot().trim());
        policyVersion.setVersionLabel(normalizedVersionLabel);
        policyVersion.setEffectiveFrom(request.getEffectiveFrom());

        boolean active = request.getActive() == null || request.getActive();
        policyVersion.setActive(active);

        if (active) {
            deactivateOtherVersions(request.getOrganizationId(), normalizedCode, null);
        }

        PolicyVersion saved = policyVersionRepository.save(policyVersion);
        return toResponseDTO(saved);
    }

    @Transactional
    public PolicyVersionResponseDTO update(UUID id, PolicyVersionUpdateRequestDTO request) {
        PolicyVersion policyVersion = findOrThrow(id);

        String normalizedCode = request.getCode().trim().toUpperCase();
        String normalizedVersionLabel = request.getVersionLabel().trim().toUpperCase();

        policyVersionRepository.findByOrganizationIdAndCodeAndVersionLabel(
                policyVersion.getOrganizationId(),
                normalizedCode,
                normalizedVersionLabel
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otra versión de política con ese código y versión en la organización."
                );
            }
        });

        policyVersion.setCode(normalizedCode);
        policyVersion.setTitle(request.getTitle().trim());
        policyVersion.setContentSnapshot(request.getContentSnapshot().trim());
        policyVersion.setVersionLabel(normalizedVersionLabel);
        policyVersion.setEffectiveFrom(request.getEffectiveFrom());

        if (policyVersion.isActive()) {
            deactivateOtherVersions(policyVersion.getOrganizationId(), normalizedCode, policyVersion.getId());
        }

        PolicyVersion saved = policyVersionRepository.save(policyVersion);
        return toResponseDTO(saved);
    }

    @Transactional
    public PolicyVersionResponseDTO updateStatus(UUID id, PolicyVersionStatusUpdateRequestDTO request) {
        PolicyVersion policyVersion = findOrThrow(id);

        if (request.isActive()) {
            deactivateOtherVersions(policyVersion.getOrganizationId(), policyVersion.getCode(), policyVersion.getId());
        }

        policyVersion.setActive(request.isActive());

        PolicyVersion saved = policyVersionRepository.save(policyVersion);
        return toResponseDTO(saved);
    }

    private void deactivateOtherVersions(UUID organizationId, String code, UUID currentId) {
        List<PolicyVersion> versions = policyVersionRepository.findByOrganizationIdAndCode(organizationId, code);

        for (PolicyVersion version : versions) {
            if (currentId == null || !version.getId().equals(currentId)) {
                version.setActive(false);
            }
        }

        policyVersionRepository.saveAll(versions);
    }

    private PolicyVersion findOrThrow(UUID id) {
        return policyVersionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Versión de política no encontrada."
                ));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private PolicyVersionResponseDTO toResponseDTO(PolicyVersion policyVersion) {
        PolicyVersionResponseDTO dto = new PolicyVersionResponseDTO();
        dto.setId(policyVersion.getId());
        dto.setOrganizationId(policyVersion.getOrganizationId());
        dto.setCode(policyVersion.getCode());
        dto.setTitle(policyVersion.getTitle());
        dto.setContentSnapshot(policyVersion.getContentSnapshot());
        dto.setVersionLabel(policyVersion.getVersionLabel());
        dto.setEffectiveFrom(policyVersion.getEffectiveFrom());
        dto.setActive(policyVersion.isActive());
        dto.setCreatedAt(policyVersion.getCreatedAt());
        dto.setUpdatedAt(policyVersion.getUpdatedAt());
        return dto;
    }
}