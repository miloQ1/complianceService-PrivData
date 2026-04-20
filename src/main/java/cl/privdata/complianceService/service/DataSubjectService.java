package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.DataSubjectCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.DataSubjectStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.DataSubjectUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.DataSubjectResponseDTO;
import cl.privdata.complianceService.model.DataSubject;
import cl.privdata.complianceService.model.enums.DataSubjectStatus;
import cl.privdata.complianceService.repository.DataSubjectRepository;
import jakarta.transaction.Transactional;

@Service
public class DataSubjectService {

    private final DataSubjectRepository dataSubjectRepository;

    public DataSubjectService(DataSubjectRepository dataSubjectRepository) {
        this.dataSubjectRepository = dataSubjectRepository;
    }

    public List<DataSubjectResponseDTO> getAll(UUID organizationId, DataSubjectStatus status, String search) {
        List<DataSubject> dataSubjects = dataSubjectRepository.search(
                organizationId,
                status,
                normalizeSearch(search)
        );

        List<DataSubjectResponseDTO> response = new ArrayList<>();
        for (DataSubject dataSubject : dataSubjects) {
            response.add(toResponseDTO(dataSubject));
        }

        return response;
    }

    public DataSubjectResponseDTO getById(UUID id) {
        DataSubject dataSubject = findOrThrow(id);
        return toResponseDTO(dataSubject);
    }

    @Transactional
    public DataSubjectResponseDTO create(DataSubjectCreateRequestDTO request) {
        dataSubjectRepository.findByOrganizationIdAndDocumentNumber(
                request.getOrganizationId(),
                request.getDocumentNumber()
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe un titular con ese número de documento en la organización."
            );
        });

        DataSubject dataSubject = new DataSubject();
        dataSubject.setOrganizationId(request.getOrganizationId());
        dataSubject.setFirstName(request.getFirstName().trim());
        dataSubject.setLastName(request.getLastName().trim());
        dataSubject.setDocumentType(request.getDocumentType().trim());
        dataSubject.setDocumentNumber(request.getDocumentNumber().trim());
        dataSubject.setEmail(request.getEmail());
        dataSubject.setPhone(request.getPhone());
        dataSubject.setAddress(request.getAddress());
        dataSubject.setStatus(DataSubjectStatus.ACTIVE);

        DataSubject saved = dataSubjectRepository.save(dataSubject);
        return toResponseDTO(saved);
    }

    @Transactional
    public DataSubjectResponseDTO update(UUID id, DataSubjectUpdateRequestDTO request) {
        DataSubject dataSubject = findOrThrow(id);

        dataSubjectRepository.findByOrganizationIdAndDocumentNumber(
                dataSubject.getOrganizationId(),
                request.getDocumentNumber()
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ya existe otro titular con ese número de documento en la organización."
                );
            }
        });

        dataSubject.setFirstName(request.getFirstName().trim());
        dataSubject.setLastName(request.getLastName().trim());
        dataSubject.setDocumentType(request.getDocumentType().trim());
        dataSubject.setDocumentNumber(request.getDocumentNumber().trim());
        dataSubject.setEmail(request.getEmail());
        dataSubject.setPhone(request.getPhone());
        dataSubject.setAddress(request.getAddress());

        DataSubject saved = dataSubjectRepository.save(dataSubject);
        return toResponseDTO(saved);
    }

    @Transactional
    public DataSubjectResponseDTO updateStatus(UUID id, DataSubjectStatusUpdateRequestDTO request) {
        DataSubject dataSubject = findOrThrow(id);
        dataSubject.setStatus(request.getStatus());

        DataSubject saved = dataSubjectRepository.save(dataSubject);
        return toResponseDTO(saved);
    }

    private DataSubject findOrThrow(UUID id) {
        return dataSubjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Titular no encontrado."
                ));
    }

    private String normalizeSearch(String search) {
        if (search == null || search.isBlank()) {
            return null;
        }
        return search.trim();
    }

    private DataSubjectResponseDTO toResponseDTO(DataSubject dataSubject) {
        DataSubjectResponseDTO dto = new DataSubjectResponseDTO();
        dto.setId(dataSubject.getId());
        dto.setOrganizationId(dataSubject.getOrganizationId());
        dto.setFirstName(dataSubject.getFirstName());
        dto.setLastName(dataSubject.getLastName());
        dto.setFullName(dataSubject.getFirstName() + " " + dataSubject.getLastName());
        dto.setDocumentType(dataSubject.getDocumentType());
        dto.setDocumentNumber(dataSubject.getDocumentNumber());
        dto.setEmail(dataSubject.getEmail());
        dto.setPhone(dataSubject.getPhone());
        dto.setAddress(dataSubject.getAddress());
        dto.setStatus(dataSubject.getStatus());
        dto.setCreatedAt(dataSubject.getCreatedAt());
        dto.setUpdatedAt(dataSubject.getUpdatedAt());
        return dto;
    }
}
