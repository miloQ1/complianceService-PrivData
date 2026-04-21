package cl.privdata.complianceService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.privdata.complianceService.DTO.request.ProcessingActivityDataCategoryCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingActivityDataCategoryReplaceRequestDTO;
import cl.privdata.complianceService.DTO.response.ProcessingActivityDataCategoryResponseDTO;
import cl.privdata.complianceService.model.ProcessingActivityDataCategory;
import cl.privdata.complianceService.repository.ProcessingActivityDataCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class ProcessingActivityDataCategoryService {

    private final ProcessingActivityDataCategoryRepository processingActivityDataCategoryRepository;

    public ProcessingActivityDataCategoryService(
            ProcessingActivityDataCategoryRepository processingActivityDataCategoryRepository
    ) {
        this.processingActivityDataCategoryRepository = processingActivityDataCategoryRepository;
    }

    public List<ProcessingActivityDataCategoryResponseDTO> getAll(
            UUID processingActivityId,
            UUID personalDataCategoryId
    ) {
        List<ProcessingActivityDataCategory> items;

        if (processingActivityId != null) {
            items = processingActivityDataCategoryRepository.findByProcessingActivityId(processingActivityId);
        } else if (personalDataCategoryId != null) {
            items = processingActivityDataCategoryRepository.findByPersonalDataCategoryId(personalDataCategoryId);
        } else {
            items = processingActivityDataCategoryRepository.findAll();
        }

        List<ProcessingActivityDataCategoryResponseDTO> response = new ArrayList<>();
        for (ProcessingActivityDataCategory item : items) {
            response.add(toResponseDTO(item));
        }

        return response;
    }

    public ProcessingActivityDataCategoryResponseDTO getById(UUID id) {
        ProcessingActivityDataCategory item = findOrThrow(id);
        return toResponseDTO(item);
    }

    @Transactional
    public ProcessingActivityDataCategoryResponseDTO create(ProcessingActivityDataCategoryCreateRequestDTO request) {
        processingActivityDataCategoryRepository.findByProcessingActivityIdAndPersonalDataCategoryId(
                request.getProcessingActivityId(),
                request.getPersonalDataCategoryId()
        ).ifPresent(existing -> {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La relación entre actividad y categoría ya existe."
            );
        });

        ProcessingActivityDataCategory item = new ProcessingActivityDataCategory();
        item.setProcessingActivityId(request.getProcessingActivityId());
        item.setPersonalDataCategoryId(request.getPersonalDataCategoryId());

        ProcessingActivityDataCategory saved = processingActivityDataCategoryRepository.save(item);
        return toResponseDTO(saved);
    }

    @Transactional
    public List<ProcessingActivityDataCategoryResponseDTO> replaceCategories(
            UUID processingActivityId,
            ProcessingActivityDataCategoryReplaceRequestDTO request
    ) {
        processingActivityDataCategoryRepository.deleteByProcessingActivityId(processingActivityId);

        List<ProcessingActivityDataCategoryResponseDTO> response = new ArrayList<>();

        for (UUID categoryId : request.getPersonalDataCategoryIds()) {
            ProcessingActivityDataCategory item = new ProcessingActivityDataCategory();
            item.setProcessingActivityId(processingActivityId);
            item.setPersonalDataCategoryId(categoryId);

            ProcessingActivityDataCategory saved = processingActivityDataCategoryRepository.save(item);
            response.add(toResponseDTO(saved));
        }

        return response;
    }

    @Transactional
    public void delete(UUID id) {
        ProcessingActivityDataCategory item = findOrThrow(id);
        processingActivityDataCategoryRepository.delete(item);
    }

    private ProcessingActivityDataCategory findOrThrow(UUID id) {
        return processingActivityDataCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Relación actividad-categoría no encontrada."
                ));
    }

    private ProcessingActivityDataCategoryResponseDTO toResponseDTO(ProcessingActivityDataCategory item) {
        ProcessingActivityDataCategoryResponseDTO dto = new ProcessingActivityDataCategoryResponseDTO();
        dto.setId(item.getId());
        dto.setProcessingActivityId(item.getProcessingActivityId());
        dto.setPersonalDataCategoryId(item.getPersonalDataCategoryId());
        dto.setCreatedAt(item.getCreatedAt());
        return dto;
    }
}
