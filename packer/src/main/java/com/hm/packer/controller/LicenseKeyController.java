package com.hm.packer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/licenseKey")
public class LicenseKeyController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String licenseAuthView(){
        return "licenseAuth";
    }

}
