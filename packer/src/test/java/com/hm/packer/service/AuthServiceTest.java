package com.hm.packer.service;

import com.hm.packer.application.security.encrypt.PackerSafeAES;
import com.hm.packer.application.security.token.Certified;
import com.hm.packer.model.dto.EngineerAuthDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AuthServiceTest {

//    @Autowired
//    AuthService authService;
//
//    @Autowired
//    PackerSafeAES aes;
//
//    @Test
//    void onlineAuth() throws Exception {
//
//        Certified c = authService.onlineAuth(
//                EngineerAuthDto.builder()
//                .id("test")
//                .password("test")
//                .build()
//        );
//
//        assertThat(c.getEngineer().getId()).isEqualTo("test");
//    }
}