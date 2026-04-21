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

import cl.privdata.complianceService.DTO.request.SecurityMeasureCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.SecurityMeasureImplementedUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.SecurityMeasureUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.SecurityMeasureResponseDTO;
import cl.privdata.complianceService.model.enums.SecurityMeasureType;
import cl.privdata.complianceService.service.SecurityMeasureService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/security-measures")
public class SecurityMeasureController {

    private final SecurityMeasureService securityMeasureService;

    public SecurityMeasureController(SecurityMeasureService securityMeasureService) {
        this.securityMeasureService = securityMeasureService;
    }

    @GetMapping
    public ResponseEntity<List<SecurityMeasureResponseDTO>> getAll(
            @RequestParam(required = false) UUID processingActivityId,
            @RequestParam(required = false) Boolean implemented,
            @RequestParam(required = false) SecurityMeasureType measureType,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(
                securityMeasureService.getAll(processingActivityId, implemented, measureType, search)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityMeasureResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(securityMeasureService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityMeasureResponseDTO> create(
            @Valid @RequestBody SecurityMeasureCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(securityMeasureService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityMeasureResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody SecurityMeasureUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(securityMeasureService.update(id, request));
    }

    @PatchMapping("/{id}/implemented")
    public ResponseEntity<SecurityMeasureResponseDTO> updateImplemented(
            @PathVariable UUID id,
            @RequestBody SecurityMeasureImplementedUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(securityMeasureService.updateImplemented(id, request));
    }
}
