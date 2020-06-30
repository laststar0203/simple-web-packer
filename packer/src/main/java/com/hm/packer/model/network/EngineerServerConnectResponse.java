package com.hm.packer.model.network;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@Builder
public class EngineerServerConnectResponse {
    private String[] publicKeyModulus;
    private String publicKeyExponent;
    private String authCode;
}
