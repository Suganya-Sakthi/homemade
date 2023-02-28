package org.perscholas.homemade.security;

import org.perscholas.homemade.models.AuthGroup;
import org.perscholas.homemade.models.Chef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUserPrincipal implements UserDetails {

    private Chef chef;
    private List<AuthGroup> authGroup;

    public AppUserPrincipal(Chef chef, List<AuthGroup> authGroup) {
        this.chef= chef;
        this.authGroup = authGroup;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authGroup.stream().map(auth -> new SimpleGrantedAuthority(auth.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return chef.getPassword();
    }

    @Override
    public String getUsername() {
        return chef.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
