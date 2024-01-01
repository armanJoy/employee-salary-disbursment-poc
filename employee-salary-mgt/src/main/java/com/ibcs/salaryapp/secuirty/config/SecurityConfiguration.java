package com.ibcs.salaryapp.secuirty.config;

import org.springframework.beans.factory.annotation.Autowired;
import com.ibcs.salaryapp.util.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtSecurityContextRepository jwtSecurityContextRepository;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected @Override
    void configure(HttpSecurity http) throws Exception {

        http.securityContext().securityContextRepository(jwtSecurityContextRepository);

        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "**/**")
                .hasAnyRole(AppConstant.EMPLOYEE_ROLE, AppConstant.SYSADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "**/**")
                .hasAnyRole(AppConstant.EMPLOYEE_ROLE, AppConstant.SYSADMIN_ROLE)
                .antMatchers(HttpMethod.DELETE, "**/**")
                .hasAnyRole(AppConstant.EMPLOYEE_ROLE, AppConstant.SYSADMIN_ROLE)
                .antMatchers("/v1/user/create").permitAll()
                .antMatchers("/v1/user/login").permitAll()
                .anyRequest().authenticated().and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()
                .addFilter(new CustomAuthorizationFilter(authenticationManager(), new CustomAccessDeniedHandler()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security", "/swagger-ui.html", "/webjars/**")
                .antMatchers(HttpMethod.POST, "/authenticate")
                .antMatchers(HttpMethod.POST, "/v1/user/login");
    }

    public @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected @Override
    void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }

        };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
