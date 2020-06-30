package com.hm.packer.application.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockCertifiedSecurityContextFactory.class)
public @interface WIthCustomMockCertified {

    String engineerId() default "test";
    String engineerName() default "test";
    String engineerEmail() default  "test@tester.com";
    String engineerKey() default "SERW12134SE";
    String lincenseKey() default "ABASSESDSAFEFAEAFEAFEFAFEFSFSFES";
}
