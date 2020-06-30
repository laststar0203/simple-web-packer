package com.hm.packer.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EngineerAuthServerMessage<T> {

    private boolean success;
    private String message;
    private T data;

}
