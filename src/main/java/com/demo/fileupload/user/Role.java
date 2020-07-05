package com.demo.fileupload.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_WORKER, ROLE_USER;

    public String getAuthority() {
        return name();
    }
}
