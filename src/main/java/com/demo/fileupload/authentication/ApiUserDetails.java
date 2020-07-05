package com.demo.fileupload.authentication;

import com.demo.fileupload.user.Role;
import com.demo.fileupload.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ApiUserDetails extends User {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ApiUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked, Set<Role> authorities, Long id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        setUserId(id);
    }

    public ApiUserDetails(String username, String password, Set<Role> authorities, Long id) {
        this(username, password, true, true, true, true, authorities, id);
    }
}
