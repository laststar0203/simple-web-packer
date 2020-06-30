package com.hm.packer.application.security.provider;

import com.hm.packer.application.security.token.PreLoginAuthenticationToken;
import com.hm.packer.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Lazy
    @Autowired
    private AuthService authService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        return authService.onlineAuth(((PreLoginAuthenticationToken) authentication).getDto());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreLoginAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
