package org.example.authservice.helpers;

import org.example.authservice.models.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthUserDetails extends UserAccount implements UserDetails {

    //why this class
    // Because spring security works on UserDetail polymorphic type of auth
    private String username;

    private String password;

    public AuthUserDetails(UserAccount userAccount){
        this.username = userAccount.getEmail();
        this.password = userAccount.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    //Below set of method does not concern much
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
