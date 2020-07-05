package com.hm.packer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/engineer")
public class EngineerController {

    @GetMapping("login")
    public String loginPage(){
        return "engineerLogin";
    }

}
