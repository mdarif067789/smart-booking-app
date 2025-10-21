package com.smartbooking.app.dto;

import com.smartbooking.app.enums.Role;

import java.util.List;

public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;

    private List<BusinessResponseDto>businessOwned;

    // ── getters & setters ───────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<BusinessResponseDto> getBusinessOwned() {
        return businessOwned;
    }

    public void setBusinessOwned(List<BusinessResponseDto> businessOwned) {
        this.businessOwned = businessOwned;
    }
}
