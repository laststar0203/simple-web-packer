package com.hm.packer.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Engineer {

    private String id;
    private String name;
    private String email;
    private String key;


}
