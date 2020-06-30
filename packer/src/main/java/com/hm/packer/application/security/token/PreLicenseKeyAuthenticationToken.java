package com.hm.packer.application.security.token;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class PreLicenseKeyAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String key;

    public PreLicenseKeyAuthenticationToken(String key){
        this(key, "");
        this.key = key;
    }

    public PreLicenseKeyAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
