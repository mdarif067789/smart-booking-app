package com.smartbooking.app.controller;

import com.smartbooking.app.dto.ServiceRequestDto;
import com.smartbooking.app.dto.ServiceResponseDto;
import com.smartbooking.app.service.ServiceDetailsService;
import com.smartbooking.app.utils.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Service Management", description = "Endpoints for business owners to manage services")
public class ServiceDetailsController {

    private final ServiceDetailsService serviceService;

    public ServiceDetailsController(ServiceDetailsService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * POST /api/services/business/{businessId}
     * Create a new service under the specified business
     */
    @Operation(summary = "Create a service under a business")
    @PostMapping
    public ResponseEntity<ServiceResponseDto> createService(
            @Valid @RequestBody ServiceRequestDto request) {

        Long ownerId = AuthUtil.getCurrentUserId();
        return ResponseEntity.ok(serviceService.createService(request));
    }

    /**
     * GET /api/services
     * List all services under the current owner's businesses
     */
    @Operation(summary = "List all services across owner’s businesses")
    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> listAllOwnerServices() {
        return ResponseEntity.ok(serviceService.getAllServicesByOwner());
    }


    /**
     * GET /api/services/business/{businessId}
     * List services by business
     */
    @Operation(summary = "List services for a specific business")
    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<ServiceResponseDto>> listServicesByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(serviceService.getServicesByBusinessId(businessId));
    }

    /**
     * GET /api/services/provider/{providerId}
     * List services assigned to a provider
     */
    @Operation(summary = "List services by provider")
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ServiceResponseDto>> listByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(serviceService.getServicesByProvider(providerId));
    }

    /**
     * PUT /api/services/{serviceId}
     * Update service details
     */
    @Operation(summary = "Update service details")
    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> updateService(
            @PathVariable Long serviceId,
            @Valid @RequestBody ServiceRequestDto request) {
        return ResponseEntity.ok(serviceService.updateService(serviceId, request));
    }

    /**
     * DELETE /api/services/{serviceId}
     * Delete a service
     */
    @Operation(summary = "Delete service")
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete service")
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDto> fetchServiceById(@PathVariable Long serviceId) {
       return ResponseEntity.ok(serviceService.fetchServiceById(serviceId));

    }

}
