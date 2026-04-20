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

import cl.privdata.complianceService.DTO.request.ProcessingPurposeCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingPurposeStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.ProcessingPurposeUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.ProcessingPurposeResponseDTO;
import cl.privdata.complianceService.model.enums.LegalBasis;
import cl.privdata.complianceService.service.ProcessingPurposeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/processing-purposes")
public class ProcessingPurposeController {

    private final ProcessingPurposeService processingPurposeService;

    public ProcessingPurposeController(ProcessingPurposeService processingPurposeService) {
        this.processingPurposeService = processingPurposeService;
    }

    @GetMapping
    public ResponseEntity<List<ProcessingPurposeResponseDTO>> getAll(
            @RequestParam(required = false) UUID organizationId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) LegalBasis legalBasis,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(
                processingPurposeService.getAll(organizationId, active, legalBasis, search)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessingPurposeResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(processingPurposeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProcessingPurposeResponseDTO> create(
            @Valid @RequestBody ProcessingPurposeCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(processingPurposeService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessingPurposeResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProcessingPurposeUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(processingPurposeService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProcessingPurposeResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody ProcessingPurposeStatusUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(processingPurposeService.updateStatus(id, request));
    }
}