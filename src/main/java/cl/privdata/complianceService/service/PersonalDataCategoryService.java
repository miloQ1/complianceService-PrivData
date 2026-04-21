package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.PersonalDataCategoryCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.PersonalDataCategoryStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.PersonalDataCategoryUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.PersonalDataCategoryResponseDTO;
import cl.privdata.complianceService.model.PersonalDataCategory;
import cl.privdata.complianceService.repository.PersonalDataCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonalDataCategoryService {

    private final PersonalDataCategoryRepository personalDataCategoryRepository;

    public PersonalDataCategoryService(PersonalDataCategoryRepository personalDataCategoryRepository) {
        this.personalDataCategoryRepository = personalDataCategoryRepository;
    }

    public List<PersonalDataCategoryResponseDTO> getAll(
            UUID organizationId,
            Boolean active,
            Boolean sensitive,
            String search
    ) {
        List<PersonalDataCategory> categories = personalDataCategoryRepository.search(
                organizationId,
                active,
                sensitive,
                normalize(search)
        );

        List<PersonalDataCategoryResponseDTO> response = new ArrayList<>();
        for (PersonalDataCategory category : categories) {
            response.add(toResponseDTO(category));
        }

        return response;
    }

    public PersonalDataCategoryResponseDTO getById(UUID id) {
        PersonalDataCategory category = findOrThrow(id);
        return toResponseDTO(category);
    }

    @Transactional
    public PersonalDataCategoryResponseDTO create(PersonalDataCategoryCreateRequestDTO request) {
        String normalizedName = request.getName().trim();

        personalDataCategoryRepository.findByOrganizationIdAndName(
                request.getOrganizationId(),
                normalizedName
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe una categoría con ese nombre en la organización."
            );
        });

        PersonalDataCategory category = new PersonalDataCategory();
        category.setOrganizationId(request.getOrganizationId());
        category.setName(normalizedName);
        category.setDescription(request.getDescription());
        category.setSensitive(request.isSensitive());

        PersonalDataCategory saved = personalDataCategoryRepository.save(category);
        return toResponseDTO(saved);
    }

    @Transactional
    public PersonalDataCategoryResponseDTO update(UUID id, PersonalDataCategoryUpdateRequestDTO request) {
        PersonalDataCategory category = findOrThrow(id);
        String normalizedName = request.getName().trim();

        personalDataCategoryRepository.findByOrganizationIdAndName(
                category.getOrganizationId(),
                normalizedName
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otra categoría con ese nombre en la organización."
                );
            }
        });

        category.setName(normalizedName);
        category.setDescription(request.getDescription());
        category.setSensitive(request.isSensitive());

        PersonalDataCategory saved = personalDataCategoryRepository.save(category);
        return toResponseDTO(saved);
    }

    @Transactional
    public PersonalDataCategoryResponseDTO updateStatus(UUID id, PersonalDataCategoryStatusUpdateRequestDTO request) {
        PersonalDataCategory category = findOrThrow(id);
        category.setActive(request.isActive());

        PersonalDataCategory saved = personalDataCategoryRepository.save(category);
        return toResponseDTO(saved);
    }

    private PersonalDataCategory findOrThrow(UUID id) {
        return personalDataCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría de dato personal no encontrada."
                ));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private PersonalDataCategoryResponseDTO toResponseDTO(PersonalDataCategory category) {
        PersonalDataCategoryResponseDTO dto = new PersonalDataCategoryResponseDTO();
        dto.setId(category.getId());
        dto.setOrganizationId(category.getOrganizationId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setSensitive(category.isSensitive());
        dto.setActive(category.isActive());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}