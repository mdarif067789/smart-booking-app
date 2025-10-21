package com.smartbooking.app.entity;

import com.smartbooking.app.enums.IndustryType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private List<IndustryType> specialization;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceDetails serviceDetails;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<IndustryType> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<IndustryType> specialization) {
        this.specialization = specialization;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public ServiceDetails getServices() {
        return serviceDetails;
    }

    public void setServices(ServiceDetails serviceDetails) {
        this.serviceDetails = serviceDetails;
    }
}
