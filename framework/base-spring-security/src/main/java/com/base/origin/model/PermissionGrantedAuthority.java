package com.base.origin.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class PermissionGrantedAuthority implements GrantedAuthority {

    private final String permission;

    public PermissionGrantedAuthority(String permission) {
        Assert.hasText(permission, "A granted authority textual representation is required");
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof PermissionGrantedAuthority) {
            return permission.equals(((PermissionGrantedAuthority) obj).permission);
        }

        return false;
    }

    public int hashCode() {
        return permission.hashCode();
    }

    public String toString() {
        return permission;
    }

}
