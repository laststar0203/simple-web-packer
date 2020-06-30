package com.hm.packer.application.exception;


import org.springframework.security.core.AuthenticationException;

public class BadEncodingException extends AuthenticationException {
    public BadEncodingException(String msg) {
        super(msg);
    }
}
