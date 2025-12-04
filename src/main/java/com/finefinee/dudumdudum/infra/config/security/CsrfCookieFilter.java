package com.finefinee.dudumdudum.infra.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            log.debug("CSRF Token found: headerName={}, parameterName={}, token={}",
                    csrfToken.getHeaderName(), csrfToken.getParameterName(), csrfToken.getToken());
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        } else {
            log.debug("CSRF Token not found in request attribute");
        }
        filterChain.doFilter(request, response);
    }
}
