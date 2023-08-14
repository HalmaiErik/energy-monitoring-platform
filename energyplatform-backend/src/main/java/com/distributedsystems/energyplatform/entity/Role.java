package com.distributedsystems.energyplatform.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT,
    ADMIN;

    public String getAuthority() {
        return name();
    }
}
