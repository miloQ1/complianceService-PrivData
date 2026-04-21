package cl.privdata.complianceService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.privdata.complianceService.DTO.request.ProcessingActivityDataCategoryCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingActivityDataCategoryReplaceRequestDTO;
import cl.privdata.complianceService.DTO.response.ProcessingActivityDataCategoryResponseDTO;
import cl.privdata.complianceService.service.ProcessingActivityDataCategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/processing-activity-data-categories")
public class ProcessingActivityDataCategoryController {

    private final ProcessingActivityDataCategoryService processingActivityDataCategoryService;

    public ProcessingActivityDataCategoryController(
            ProcessingActivityDataCategoryService processingActivityDataCategoryService
    ) {
        this.processingActivityDataCategoryService = processingActivityDataCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProcessingActivityDataCategoryResponseDTO>> getAll(
            @RequestParam(required = false) UUID processingActivityId,
            @RequestParam(required = false) UUID personalDataCategoryId
    ) {
        return ResponseEntity.ok(
                processingActivityDataCategoryService.getAll(processingActivityId, personalDataCategoryId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessingActivityDataCategoryResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(processingActivityDataCategoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProcessingActivityDataCategoryResponseDTO> create(
            @Valid @RequestBody ProcessingActivityDataCategoryCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(processingActivityDataCategoryService.create(request));
    }

    @PutMapping("/processing-activity/{processingActivityId}")
    public ResponseEntity<List<ProcessingActivityDataCategoryResponseDTO>> replaceCategories(
            @PathVariable UUID processingActivityId,
            @Valid @RequestBody ProcessingActivityDataCategoryReplaceRequestDTO request
    ) {
        return ResponseEntity.ok(
                processingActivityDataCategoryService.replaceCategories(processingActivityId, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        processingActivityDataCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
