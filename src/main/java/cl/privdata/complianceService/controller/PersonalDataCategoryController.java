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

import cl.privdata.complianceService.DTO.request.PersonalDataCategoryCreateRequestDTO;
import cl.privdata.complianceService.DTO.request.PersonalDataCategoryStatusUpdateRequestDTO;
import cl.privdata.complianceService.DTO.request.PersonalDataCategoryUpdateRequestDTO;
import cl.privdata.complianceService.DTO.response.PersonalDataCategoryResponseDTO;
import cl.privdata.complianceService.service.PersonalDataCategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compliance/personal-data-categories")
public class PersonalDataCategoryController {

    private final PersonalDataCategoryService personalDataCategoryService;

    public PersonalDataCategoryController(PersonalDataCategoryService personalDataCategoryService) {
        this.personalDataCategoryService = personalDataCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<PersonalDataCategoryResponseDTO>> getAll(
            @RequestParam(required = false) UUID organizationId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean sensitive,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(
                personalDataCategoryService.getAll(organizationId, active, sensitive, search)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDataCategoryResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(personalDataCategoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PersonalDataCategoryResponseDTO> create(
            @Valid @RequestBody PersonalDataCategoryCreateRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personalDataCategoryService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalDataCategoryResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody PersonalDataCategoryUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(personalDataCategoryService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PersonalDataCategoryResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody PersonalDataCategoryStatusUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(personalDataCategoryService.updateStatus(id, request));
    }
}