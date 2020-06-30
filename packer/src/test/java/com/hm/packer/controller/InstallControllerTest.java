package com.hm.packer.controller;

import com.hm.packer.application.util.WIthCustomMockCertified;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class InstallControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @WIthCustomMockCertified
    void readInstallFileList() throws Exception {
        mvc.perform(get("/install/file/stat"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}