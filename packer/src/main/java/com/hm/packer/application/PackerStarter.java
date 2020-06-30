package com.hm.packer.application;

import com.hm.packer.model.entity.LicenseKey;
import com.hm.packer.model.mapper.LicenseKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Component
public class PackerStarter implements CommandLineRunner {

    @Autowired
    private LicenseKeyMapper mapper;

    @Override
    public void run(String... args) throws Exception {
        if(mapper.select() != null)
            return;
        BufferedReader reader = new BufferedReader(new FileReader(new File("licenseKey")));
        String licenseKey = reader.readLine();
        String engineerKey = reader.readLine();
        LicenseKey entity =
                LicenseKey.builder()
                .licenseKey(licenseKey)
                .engineerKey(engineerKey)
                .build();
        mapper.insert(entity);

        System.out.println("key init Success! licenseKey = "+ licenseKey + " engineerKey = " + engineerKey);

    }
}
