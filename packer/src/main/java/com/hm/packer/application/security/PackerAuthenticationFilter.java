package com.hm.packer.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.packer.application.security.token.PreLoginAuthenticationToken;
import com.hm.packer.application.security.token.PreLicenseKeyAuthenticationToken;
import com.hm.packer.application.security.token.Certified;
import com.hm.packer.model.dto.EngineerAuthDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PackerAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper mapper;

    protected PackerAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        mapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
       String requestURI = httpServletRequest.getRequestURI();

       return super.getAuthenticationManager().authenticate(
               requestURI.equals("/engineer/login/auth") ?
                        new PreLoginAuthenticationToken(mapper.readValue(httpServletRequest.getReader(), EngineerAuthDto.class)) :
                        new PreLicenseKeyAuthenticationToken(httpServletRequest.getParameter("key")));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext contextHolder = SecurityContextHolder.createEmptyContext();
        Certified token = (Certified) authResult;
        contextHolder.setAuthentication(token);
        SecurityContextHolder.setContext(contextHolder);

        chain.doFilter(request,response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
