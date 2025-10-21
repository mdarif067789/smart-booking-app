package com.smartbooking.app.controller;

import com.smartbooking.app.dto.ServiceProviderRequestDto;
import com.smartbooking.app.dto.ServiceProviderResponseDto;
import com.smartbooking.app.repository.UserRepository;
import com.smartbooking.app.service.ServiceProviderService;
import com.smartbooking.app.utils.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service/providers")
@Tag(name = "Service Provider (Owner)", description = "Owner‑restricted provider management")
public class ServiceProviderController {

    private final ServiceProviderService providerService;
    public ServiceProviderController(ServiceProviderService providerService, UserRepository userRepository) {
        this.providerService = providerService;
    }

    @Operation(summary = "Create provider in owner’s business")
    @PostMapping
    public ResponseEntity<ServiceProviderResponseDto> createProvider(
            @RequestBody ServiceProviderRequestDto serviceProviderRequest) {
        return ResponseEntity.ok(
                providerService.createProvider(serviceProviderRequest));
    }

    @Operation(summary = "Update provider")
    @PutMapping("/{providerId}")
    public ResponseEntity<ServiceProviderResponseDto> updateProvider(@PathVariable Long providerId, @RequestBody ServiceProviderRequestDto dto) {

        return ResponseEntity.ok(providerService.updateProvider(providerId, dto));
    }

    @Operation(summary = "Delete provider")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List providers for a business")
    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<ServiceProviderResponseDto>> listProviderByBusiness(@PathVariable Long businessId) {

        return ResponseEntity.ok(providerService.getProvidersByBusiness(businessId));
    }

    @Operation(summary = "List all providers owned by owner")
    @GetMapping
    public ResponseEntity<List<ServiceProviderResponseDto>> getProvidersByOwner() {
        return ResponseEntity.ok(providerService.getProvidersByOwner(AuthUtil.getCurrentUserId()));
    }

    @Operation(summary = "Get provider by ID (owner)")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceProviderResponseDto> getProviderById(@PathVariable Long id) {

        return ResponseEntity.ok(providerService.getById(id));
    }
}
