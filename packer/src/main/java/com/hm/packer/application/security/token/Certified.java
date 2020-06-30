package com.hm.packer.application.security.token;

import com.hm.packer.model.dto.Engineer;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

@Getter
@Builder
public class Certified extends UsernamePasswordAuthenticationToken {

    private Engineer engineer;
    private String licenseKey;

    public Certified(Engineer engineer, String licenseKey){
        super(engineer, licenseKey,
                Arrays.asList(
                        new SimpleGrantedAuthority("ENGINEER")
                ));
        this.engineer = engineer;
        this.licenseKey = licenseKey;
    }


}
