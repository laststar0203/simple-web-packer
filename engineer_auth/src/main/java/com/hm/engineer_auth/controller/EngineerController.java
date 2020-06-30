package com.hm.engineer_auth.controller;

import com.hm.engineer_auth.model.dto.EngineerAuthDto;
import com.hm.engineer_auth.model.entity.Engineer;
import com.hm.engineer_auth.model.network.Message;
import com.hm.engineer_auth.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/engineer")
public class EngineerController {

    @Autowired
    EngineerService engineerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody Engineer resource){
        try{
            engineerService.create(resource);
            return Message.of(true, "Register Success", null);
        }catch (Exception e){
            return Message.of(false, e.getMessage(), null);
        }
    }
//http://localhost:8080/engineer/auth
    @PostMapping("auth")
    @ResponseStatus(HttpStatus.OK)
    public Message login(HttpServletRequest request,
                         @RequestBody EngineerAuthDto dto){
        try{
            return Message.of(true, "Login Success", engineerService.login(dto));
        }catch (Exception e){
            e.printStackTrace();
            return Message.of(false, e.getMessage(), null);
        }
    }
}
