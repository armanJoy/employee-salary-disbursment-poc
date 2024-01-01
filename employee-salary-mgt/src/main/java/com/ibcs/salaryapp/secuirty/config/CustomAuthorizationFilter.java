package com.ibcs.salaryapp.secuirty.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ibcs.salaryapp.secuirty.config.TokenManager.TOKEN_PREFIX;
import static com.ibcs.salaryapp.secuirty.config.TokenManager.parseTokenBody;
import com.ibcs.salaryapp.util.AppConstant;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

    private final AccessDeniedHandler accessDeniedHandler;

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager,
            AccessDeniedHandler accessDeniedHandler) {
        super(authenticationManager);
        this.accessDeniedHandler = accessDeniedHandler;
    }

    protected @Override
    void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(AppConstant.AUTH_HEADER_KEY);

        if (Objects.isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        if (header.startsWith(TOKEN_PREFIX)) {
            try {
                // UsernamePasswordAuthenticationToken authentication =
                // getAuthentication(header);

                SecurityContextHolder.getContext().getAuthentication();

                // SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } catch (Exception e) {
                accessDeniedHandler.handle(request, response, new AccessDeniedException(e.getLocalizedMessage(), e));
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {

        if (header.startsWith(TOKEN_PREFIX)) {

            Claims claims = parseTokenBody(header);
            String username = (String) claims.get(Claims.SUBJECT);
            final Collection<? extends GrantedAuthority> authorities = Arrays
                    .stream(claims.get("roles").toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } else {
            throw new AccessDeniedException("Failed to parse authentication token");
        }
    }

}
