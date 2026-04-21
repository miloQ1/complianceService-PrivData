package cl.privdata.complianceService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.privdata.complianceService.DTO.request.ProcessingActivityCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingActivityStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingActivityUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.ProcessingActivityResponseDTO;
import cl.privdata.complianceService.model.enums.ProcessingActivityRiskLevel;
import cl.privdata.complianceService.model.enums.ProcessingActivityStatus;
import cl.privdata.complianceService.service.ProcessingActivityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/processing-activities")
public class ProcessingActivityController {

    private final ProcessingActivityService processingActivityService;

    public ProcessingActivityController(ProcessingActivityService processingActivityService) {
        this.processingActivityService = processingActivityService;
    }

    @GetMapping
    public ResponseEntity<List<ProcessingActivityResponseDTO>> getAll(
            @RequestParam(required = false) UUID organizationId,
            @RequestParam(required = false) ProcessingActivityStatus status,
            @RequestParam(required = false) ProcessingActivityRiskLevel riskLevel,
            @RequestParam(required = false) UUID purposeId,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(
                processingActivityService.getAll(organizationId, status, riskLevel, purposeId, search)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessingActivityResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(processingActivityService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProcessingActivityResponseDTO> create(
            @Valid @RequestBody ProcessingActivityCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(processingActivityService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessingActivityResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProcessingActivityUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(processingActivityService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProcessingActivityResponseDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody ProcessingActivityStatusUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(processingActivityService.updateStatus(id, request));
    }
}