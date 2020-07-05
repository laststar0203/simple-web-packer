package com.hm.packer.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Package {

    private int number;

    private String name;

    private String version;

    private int installed;
}
