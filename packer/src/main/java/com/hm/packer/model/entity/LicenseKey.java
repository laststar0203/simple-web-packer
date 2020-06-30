package com.hm.packer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LicenseKey {
    private String licenseKey;
    private String engineerKey;
}
