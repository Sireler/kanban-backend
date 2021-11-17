package com.sireler.kanban.config;

import com.sireler.kanban.controller.filter.FilterChainExceptionHandler;
import com.sireler.kanban.security.jwt.JwtConfigurer;
import com.sireler.kanban.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String REGISTER_ENDPOINT = "/api/v1/auth/register";

    private final JwtTokenProvider jwtTokenProvider;

    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, FilterChainExceptionHandler filterChainExceptionHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT, REGISTER_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
