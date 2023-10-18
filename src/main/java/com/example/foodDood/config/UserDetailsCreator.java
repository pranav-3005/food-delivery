package com.example.foodDood.config;

import com.example.foodDood.model.configModel.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsCreator implements UserDetails {

    //3. set variables (4 at securityConfig class)


    String userName;  //these variable names are constant  ***
    String password;

    List<GrantedAuthority> authorities;
    public UserDetailsCreator(User user) {
        this.userName= user.getUserName();
        this.password= user.getPassword();
        this.authorities=new ArrayList<>(); //*********

        String[] roles= user.getRole().split(",");
        for (String role:roles)
        {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(role);
            this.authorities.add(simpleGrantedAuthority);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    //set all to true
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
