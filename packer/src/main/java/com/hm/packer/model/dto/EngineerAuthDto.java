package com.hm.packer.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EngineerAuthDto {

    private String id;
    private String password;

}
