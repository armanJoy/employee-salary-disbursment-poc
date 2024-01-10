package com.ibcs.salaryapp.secuirty;

import com.ibcs.salaryapp.model.domain.user.Role;
import com.ibcs.salaryapp.model.domain.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.ibcs.salaryapp.repository.user.UserRoleRepo;
import com.ibcs.salaryapp.repository.user.RoleRepo;
import com.ibcs.salaryapp.repository.user.UserInfoRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepo.findTop1ByUserEmailAndActive(username, true);
        if (user != null) {
            //return new User(user.getUserEmail(), user.getPassword(), buildSimpleGrantedAuthorities(user.getRoles()));
            return new UserAuthResponse(user, userRoleRepo, roleRepo);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

}
