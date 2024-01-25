package com.mohirdev.mohirdev.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
           Authentication authentication = tokenProvider.getAuthentacion(jwt);
           SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
    public String resolveToken(HttpServletRequest request){
        String beareToken  = request.getHeader("authorization");
        if (StringUtils.hasText(beareToken) && beareToken.startsWith("beare ")){
            return beareToken.substring(7);
        }
        return null;
    }
}
