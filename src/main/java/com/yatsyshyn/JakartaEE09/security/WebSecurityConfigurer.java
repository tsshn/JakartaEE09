package com.yatsyshyn.JakartaEE09.security;

import com.yatsyshyn.JakartaEE09.repositories.UserRepository;
import com.yatsyshyn.JakartaEE09.services.UserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static org.springframework.http.HttpMethod.POST;


@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final UserRepository usersRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilter(logoutFilter())
                .addFilterBefore(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new UserDetailsService(usersRepository);
    }

    @Bean
    @SneakyThrows
    @Override
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    private LoginFilter customLoginFilter() {
        final LoginFilter customLoginFilter = new LoginFilter(authenticationManagerBean(), objectMapper);
        customLoginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", POST.name()));
        return customLoginFilter;
    }

    private LogoutFilter logoutFilter() {
        final LogoutHandler[] logoutHandler = new LogoutHandler[]{
                new SecurityContextLogoutHandler()
        };
        return new LogoutFilter(new LogoutAdapter(), logoutHandler);
    }
}
