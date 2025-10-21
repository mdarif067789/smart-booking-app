package com.smartbooking.app.dto;

import com.smartbooking.app.enums.IndustryType;

import java.util.List;

public class ServiceProviderRequestDto {

    private String name;
    private String email;
    private String phone;
    private List<IndustryType> specialization;
    private Long serviceId;

    private Long businessId;

    /* getters / setters */
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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
