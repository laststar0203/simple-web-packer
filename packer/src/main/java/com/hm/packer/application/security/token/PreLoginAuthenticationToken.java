package com.hm.packer.application.security.token;

import lombok.Getter;
import com.hm.packer.model.dto.EngineerAuthDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class PreLoginAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private EngineerAuthDto dto;

    public PreLoginAuthenticationToken(EngineerAuthDto dto){
        this(dto.getId(), dto.getPassword());
        this.dto = dto;
    }

    public PreLoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
