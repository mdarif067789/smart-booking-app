package com.smartbooking.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smartbooking.app.enums.IndustryType;

import java.util.List;

public class ServiceProviderResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<IndustryType> specialization;
    private Long businessId;
    private String businessName;

    /* getters / setters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<IndustryType> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<IndustryType> specialization) {
        this.specialization = specialization;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

}
