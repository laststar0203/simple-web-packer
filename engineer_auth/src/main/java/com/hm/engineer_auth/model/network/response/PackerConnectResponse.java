package com.hm.engineer_auth.model.network.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@Builder
public class PackerConnectResponse {

    private String authCode;
    private String[] publicKeyModulus;
    private String publicKeyExponent;
}
