package com.smartbooking.app.dto;

/**
 * Payload for creating or updating a Service.
 * Because the logged‑in user is the business owner, we do not need a
 * businessId field—the backend can infer it from the owner’s identity.
 * Optionally, the owner can assign a provider while creating the service.
 */
public class ServiceRequestDto {

    private String name;
    private String description;
    private Double price;          // e.g. 499.0
    private Integer durationMinutes;

    private Long businessId;
    /* ---------- Getters & Setters ---------- */

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
