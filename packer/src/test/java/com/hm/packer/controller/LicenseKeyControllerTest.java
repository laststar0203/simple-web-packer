package com.hm.packer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.packer.model.dto.EngineerAuthDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LicenseKeyControllerTest {


    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;


    @Test
    void licenseView() throws Exception {
        mvc.perform(get("/licenseKey"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void licenseKeyAuth() throws Exception {
        String key = "ABASSESDSAFEFAEAFEAFEFAFEFSFSFES";
        mvc.perform(get("/licenseKey/auth?key="+key))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void failLicenseKeyAuth() throws Exception {
        String key = "ABASSESDSAFEFAEAFEAFEFAsdfsdFEFSFSFES";
        mvc.perform(get("/licenseKey/auth?key="+key)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(
                        EngineerAuthDto.builder()
                                .id("test")
                                .password("tesst")
                                .build()
                ))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}