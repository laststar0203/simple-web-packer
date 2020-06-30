package com.hm.engineer_auth.model.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Engineer {
    private String id;
    private String name;
    private String email;
    private String password;
    private String key;

}
