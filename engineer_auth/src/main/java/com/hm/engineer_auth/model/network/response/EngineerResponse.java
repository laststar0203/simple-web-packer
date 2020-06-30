package com.hm.engineer_auth.model.network.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EngineerResponse {
    private String id;
    private String name;
    private String email;
    private String key;
}
