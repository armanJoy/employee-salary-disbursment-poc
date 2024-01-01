package com.ibcs.salaryapp.secuirty.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserConverter {

    public static User toUser(User user) {
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public static UsernamePasswordAuthenticationToken toAuthenticationToken(User user) {
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

}
