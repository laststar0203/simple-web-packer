package com.hm.packer.controller;


import com.hm.packer.application.recipe.RecipeProvider;
import com.hm.packer.model.entity.Package;
import com.hm.packer.model.network.Message;
import com.hm.packer.service.LinuxInstallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/install")
public class InstallController {

    @Autowired
    LinuxInstallService linuxInstallService;

    @Autowired
    RecipeProvider provider;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView installView(){
        ModelAndView mView = new ModelAndView("/installFilesStat");
        mView.addObject("installFiles", Arrays.stream(provider.getRecipes()).map(recipe ->
                Package.builder()
                        .name(recipe.getFileName())
                        .version(recipe.getFileVersion())
                        .number(recipe.getNumber())
                        .installed(recipe.isInstalled() ? 1 : 0).build()).toArray());
        return  mView;
    }

    @GetMapping("{number}")
    @ResponseStatus(HttpStatus.OK)
    public Message install(
            @PathVariable("number") int number,
            @RequestParam Map<String, String> installParam){
        try {
            linuxInstallService.install(number, installParam);
            return Message.of(true, "Install is Done!" ,null);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.of(false , e.getMessage(), null);
        }
    }

    @Deprecated
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
    public ModelAndView installFileView(@PathVariable("number") int number){
        ModelAndView mView = new ModelAndView("/installFile");
        try {
            mView.addObject("installFile", linuxInstallService.readInstallFile(number));
        } catch (Exception e) {
            mView.setViewName("/errorMessage");
            mView.addObject("errorMessage", e.getMessage());
            e.printStackTrace();
        }
        return mView;
    }
}
