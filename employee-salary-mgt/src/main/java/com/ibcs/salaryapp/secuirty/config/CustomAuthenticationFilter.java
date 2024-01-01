package com.ibcs.salaryapp.secuirty.config;

import com.ibcs.salaryapp.model.domain.user.UserInfo;
import com.ibcs.salaryapp.secuirty.UserAuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ibcs.salaryapp.secuirty.config.TokenManager.TOKEN_PREFIX;
import static com.ibcs.salaryapp.secuirty.config.TokenManager.generateToken;
import com.ibcs.salaryapp.util.AppConstant;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(CustomUserConverter.toAuthenticationToken(user));
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Failed to resolve authentication credentials", e);
        }
    }

    protected @Override
    void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.addHeader(AppConstant.AUTH_HEADER_KEY,
                TOKEN_PREFIX + generateToken(((UserAuthResponse) authResult.getPrincipal())));
    }

}
