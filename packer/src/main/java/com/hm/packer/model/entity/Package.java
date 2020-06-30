package com.hm.packer.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Package {

    private Integer number;

    private String name;

    private String version;

    private int installed;
}
