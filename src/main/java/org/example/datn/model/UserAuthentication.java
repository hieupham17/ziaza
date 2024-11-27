package org.example.datn.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hoangKhong
 */
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAuthentication implements Authentication {

    Long userId;
    String userName, fullName;

    @Getter
    String email, phone;

    List<String> authorities;

    @Getter
    String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(authorities)) {
            return Collections.emptyList();
        }
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    /**
     * return userName
     */
    public String getCredentials() {
        return userName;
    }

    @Override
    public UserAuthentication getDetails() {
        return null;
    }

    @Override
    /**
     * return userId;
     */
    public Long getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return Objects.nonNull(userName);
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new IllegalArgumentException("method.not.allowed");
    }

    @Override
    public String getName() {
        return StringUtils.isEmpty(fullName) ? userName : fullName;
    }
}
