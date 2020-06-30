package com.hm.packer.application.security.provider;

import com.hm.packer.service.AuthService;
import com.hm.packer.application.security.token.PreLicenseKeyAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LicenseKeyAuthenticationProvider implements AuthenticationProvider {

    @Lazy
    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authService.offlineAuth(((PreLicenseKeyAuthenticationToken) authentication).getKey());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreLicenseKeyAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
