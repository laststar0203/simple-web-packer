package com.hm.packer.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Recipe {

    private int number;
    private String fileName;
    private String fileVersion;
    private String storagePath;

    @Setter
    private String installScript;
    @Setter
    private List<String> installProperties;

    @Setter
    private List<String> doScripts;

    @Setter
    private String checkScript;
    @Setter
    private String assertPos;
    @Setter
    private String assertValue;

    @Setter
    private String deleteScript;

    @Setter
    private boolean installed;

    public Recipe(int number, String fileName, String fileVersion, String storagePath){
        this.number = number;
        this.fileName = fileName;
        this.fileVersion = fileVersion;
        this.storagePath = storagePath;
    }


}
