package com.hm.packer.controller;


import com.hm.packer.model.network.Message;
import com.hm.packer.service.LinuxInstallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/install")
public class InstallController {

    @Autowired
    LinuxInstallService linuxInstallService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String installPage(){
        return "install";
    }

    @PostMapping("{number}")
    @ResponseStatus(HttpStatus.OK)
    public Message install(
            @PathVariable("number") int number,
            @RequestBody Map<String, String> installParam){
        try {
            linuxInstallService.install(number, installParam);
            return Message.of(true, "Install is Done!" ,null);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.of(false , e.getMessage(), null);
        }
    }

    @GetMapping("file/stat")
    @ResponseStatus(HttpStatus.OK)
    public Message readInstallFileList(){
        try{
            return Message.of(true, "This is InstallFileList", linuxInstallService.readInstallFileList());
        }catch (Exception e){
            return  Message.of(false, e.getMessage(), null);
        }
    }

    @GetMapping("file/{number}")
    @ResponseStatus(HttpStatus.OK)
    public Message readInstallFile(@PathVariable("number") int number){
        try{
            return Message.of(true, "This is Package", linuxInstallService.readInstallFile(number));
        }catch (Exception e){
            return  Message.of(false, e.getMessage(), null);
        }
    }


}
