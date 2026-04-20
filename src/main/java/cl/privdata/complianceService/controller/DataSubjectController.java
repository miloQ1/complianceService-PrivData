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

import cl.privdata.complianceService.DTO.request.DataSubjectCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.DataSubjectStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.DataSubjectUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.DataSubjectResponseDTO;
import cl.privdata.complianceService.model.enums.DataSubjectStatus;
import cl.privdata.complianceService.service.DataSubjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/data-subjects")
public class DataSubjectController {

    private final DataSubjectService dataSubjectService;

    public DataSubjectController(DataSubjectService dataSubjectService) {
        this.dataSubjectService = dataSubjectService;
    }

    @GetMapping
    public ResponseEntity<List<DataSubjectResponseDTO>> getAll(
            @RequestParam(required = false) UUID organizationId,
            @RequestParam(required = false) DataSubjectStatus status,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(dataSubjectService.getAll(organizationId, status, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataSubjectResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(dataSubjectService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DataSubjectResponseDTO> create(
            @Valid @RequestBody DataSubjectCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dataSubjectService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataSubjectResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody DataSubjectUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(dataSubjectService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DataSubjectResponseDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody DataSubjectStatusUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(dataSubjectService.updateStatus(id, request));
    }
}