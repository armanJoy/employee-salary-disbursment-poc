package com.ibcs.salaryapp.secuirty.config;

import com.ibcs.salaryapp.secuirty.UserAuthResponse;
import static com.ibcs.salaryapp.secuirty.config.TokenManager.TOKEN_PREFIX;
import static com.ibcs.salaryapp.secuirty.config.TokenManager.parseTokenBody;
import com.ibcs.salaryapp.util.AppConstant;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpRequestResponseHolder;

@Component
public class JwtSecurityContextRepository implements SecurityContextRepository {

    @Autowired
    private UserJwtTokenRepository userJwtTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public JwtSecurityContextRepository() {
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder request) {

        String header = request.getRequest().getHeader(AppConstant.AUTH_HEADER_KEY);

        String token = extractTokenFromRequest(request.getRequest());
        if (token != null && header != null) {
            UserJwtToken userJwtToken = userJwtTokenRepository.findByToken(token);
            if (userJwtToken != null) {
                UserAuthResponse userDetails
                        = (UserAuthResponse) userDetailsService.loadUserByUsername(userJwtToken.getUserId());
                SecurityContext context = new SecurityContextImpl();
                context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

                return context;
            }
        }
        return SecurityContextHolder.getContext();
//        .createEmptyContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        if (context != null && context.getAuthentication() != null) {
            try {
                UserAuthResponse userDetails = (UserAuthResponse) context.getAuthentication().getPrincipal();

//        String subject = tokenManager.parseTokenBody(token).getSubject();
//
//        String[] uidAndDeviceId = subject.split("[,]", 0);
//
//        String userId = uidAndDeviceId[0];
//        String deviceId = uidAndDeviceId[1];
//
                if (userDetails != null) {
                    UserJwtToken userJwtToken = userJwtTokenRepository.findByUserId(userDetails.getUsername());
                    String token = extractTokenFromRequest(request);
                    if (userJwtToken == null && token != null) {
                        userJwtToken = new UserJwtToken();
                        userJwtToken.setUserId(userDetails.getUsername());
                        userJwtToken.setToken(token);
                        userJwtTokenRepository.save(userJwtToken);
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {

        String token = extractTokenFromRequest(request);
        return token != null && userJwtTokenRepository.findByToken(token) != null;

    }

    private String extractTokenFromRequest(HttpServletRequest request) {

        String header = request.getHeader(AppConstant.AUTH_HEADER_KEY);

        String token = (header != null) ? header.replace(AppConstant.TOKEN_PREFIX, "") : null;

        return token;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {

        if (header.startsWith(TOKEN_PREFIX)) {

            Claims claims = parseTokenBody(header);
            String username = (String) claims.get(Claims.SUBJECT);
            final Collection<? extends GrantedAuthority> authorities
                    = Arrays.stream(claims.get("roles").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } else {
            throw new AccessDeniedException("Failed to parse authentication token");
        }
    }

}
