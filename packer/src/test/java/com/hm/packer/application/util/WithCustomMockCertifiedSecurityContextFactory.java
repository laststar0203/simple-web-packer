package com.hm.packer.application.util;

import com.hm.packer.application.security.token.Certified;
import com.hm.packer.model.dto.Engineer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockCertifiedSecurityContextFactory implements WithSecurityContextFactory<WIthCustomMockCertified> {


    @Override
    public SecurityContext createSecurityContext(WIthCustomMockCertified wIthCustomMockCertified) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Engineer engineer =
                Engineer.builder()
                .id(wIthCustomMockCertified.engineerId())
                .name(wIthCustomMockCertified.engineerName())
                .email(wIthCustomMockCertified.engineerEmail())
                .key(wIthCustomMockCertified.engineerKey())
                .build();
        context.setAuthentication(
                Certified.builder()
                .engineer(engineer)
                .licenseKey(wIthCustomMockCertified.lincenseKey())
                .build()
        );
        System.out.println(context.getAuthentication());
        return context;
    }
}
