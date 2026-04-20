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

import cl.privdata.complianceService.DTO.request.PolicyVersionCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.PolicyVersionStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.PolicyVersionUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.PolicyVersionResponseDTO;
import cl.privdata.complianceService.service.PolicyVersionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/policy-versions")
public class PolicyVersionController {

    private final PolicyVersionService policyVersionService;

    public PolicyVersionController(PolicyVersionService policyVersionService) {
        this.policyVersionService = policyVersionService;
    }

    @GetMapping
    public ResponseEntity<List<PolicyVersionResponseDTO>> getAll(
            @RequestParam(required = false) UUID organizationId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(policyVersionService.getAll(organizationId, active, code, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyVersionResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(policyVersionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PolicyVersionResponseDTO> create(
            @Valid @RequestBody PolicyVersionCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(policyVersionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyVersionResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody PolicyVersionUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(policyVersionService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PolicyVersionResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody PolicyVersionStatusUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(policyVersionService.updateStatus(id, request));
    }
}