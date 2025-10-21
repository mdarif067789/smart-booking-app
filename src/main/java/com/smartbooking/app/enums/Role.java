package com.smartbooking.app.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, PROVIDER, OWNER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

