package com.smartbooking.app.controller;

import com.smartbooking.app.dto.BusinessRequestDto;
import com.smartbooking.app.dto.BusinessResponseDto;
import com.smartbooking.app.dto.UserResponseDto;
import com.smartbooking.app.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
@Tag(name = "Business", description = "Business CRUD operations")

public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Operation(summary = "Get All Owners")
    @GetMapping("/owners")
    public ResponseEntity<List<UserResponseDto>> getAllOwners() {

        return ResponseEntity.ok(businessService.getAllBusinessOwners());
    }

    @Operation(summary = "Create business (owner only)")
    @PostMapping("/owners/{ownerId}")
    public ResponseEntity<BusinessResponseDto> createBusiness(
            @Valid @RequestBody BusinessRequestDto businessDto,
            @Parameter(description = "Owner ID") @PathVariable Long ownerId) {

        return ResponseEntity.ok(businessService.createBusiness(businessDto, ownerId));
    }
    @Operation(summary = "Get all businesses (public)")
    @GetMapping
    public ResponseEntity<List<BusinessResponseDto>> getAll() {
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }

    @Operation(summary = "Get businesses by owner")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<BusinessResponseDto>> getByOwner(
            @Parameter(description = "Owner ID") @PathVariable Long ownerId) {

        return ResponseEntity.ok(businessService.getBusinessesByOwner(ownerId));
    }

    @Operation(summary = "Update a business (owner only)")
    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponseDto> update(
            @Parameter(description = "Owner ID") @PathVariable Long ownerId,
            @Parameter(description = "Business ID") @PathVariable Long businessId,
            @Valid @RequestBody BusinessRequestDto updatedDto) {

        return ResponseEntity.ok(businessService.updateBusiness(ownerId,businessId,updatedDto));
    }

    @Operation(summary = "Delete a business (owner only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Owner ID") @PathVariable Long ownerId,
            @Parameter(description = "Business ID") @PathVariable Long businessId) {

        businessService.deleteBusiness(ownerId, businessId);
        return ResponseEntity.noContent().build();
    }


}
