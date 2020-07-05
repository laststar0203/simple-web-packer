package com.hm.packer.model.network;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseRecipe {

    private int number;
    private String fileName;
    private String fileVersion;
    private List<String> installProperties;
    private boolean installed;

}
