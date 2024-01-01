package com.ibcs.salaryapp.secuirty;

import java.util.*;

import com.ibcs.salaryapp.model.domain.user.Role;
import com.ibcs.salaryapp.model.domain.user.UserRole;
import com.ibcs.salaryapp.model.domain.user.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.ibcs.salaryapp.repository.user.UserRoleRepo;
import com.ibcs.salaryapp.repository.user.RoleRepo;

public class UserAuthResponse implements UserDetails {

    private final UserInfo usersLogin;
    private final UserRoleRepo userRoleRepository;
    private final RoleRepo roleRepository;

    public UserAuthResponse(UserInfo usersLogin, UserRoleRepo userRoleRepository,
            RoleRepo roleRepository) {
        this.usersLogin = usersLogin;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole userRole = userRoleRepository.findByUserId(usersLogin.getId());
        Role role = roleRepository.findByRoleId(userRole.getRoleId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return usersLogin.getPassword();
    }

    @Override
    public String getUsername() {
        return usersLogin.getUserEmail();
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

    public UserInfo getUsersLogin() {
        return usersLogin;
    }

}
