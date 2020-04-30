package com.yatsyshyn.JakartaEE09.security;

import com.yatsyshyn.JakartaEE09.entities.UserDetails;

import lombok.Value;
import lombok.SneakyThrows;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    LoginFilter(final AuthenticationManager authenticationManager, final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final UserCredentials userCredentials = objectMapper.readValue(request.getInputStream(), UserCredentials.class);
        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userCredentials.getLogin(), userCredentials.getPassword(), List.of());
        return getAuthenticationManager().authenticate(authToken);
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain chain, final Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        final UserDetails authenticatedUser = (UserDetails) auth.getPrincipal();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(authenticatedUser));
    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(
            final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed
    ) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Value
    private static class UserCredentials {
        private final String login;
        private final String password;
    }
}
