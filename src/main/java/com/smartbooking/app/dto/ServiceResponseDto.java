package com.smartbooking.app.dto;

/**
 * UI‑ready information about a Service.
 * Includes flattened business and provider details for quick display.
 */
public class ServiceResponseDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationMinutes;
    private Long businessId;
    private String businessName;

    /* ---------- Getters & Setters ---------- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }


    public Long getBusinessId() { return businessId; }
    public void setBusinessId(Long businessId) { this.businessId = businessId; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
}
