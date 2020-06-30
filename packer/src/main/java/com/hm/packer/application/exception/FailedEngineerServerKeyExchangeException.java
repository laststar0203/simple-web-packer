package com.hm.packer.application.exception;


import org.springframework.security.core.AuthenticationException;

public class FailedEngineerServerKeyExchangeException extends AuthenticationException {
    public FailedEngineerServerKeyExchangeException(String msg){
        super(msg);
    }
}
