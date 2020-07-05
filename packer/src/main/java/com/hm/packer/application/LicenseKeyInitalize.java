package com.hm.packer.application;

import com.hm.packer.application.command.LinuxCommandExecutor;
import com.hm.packer.model.entity.LicenseKey;
import com.hm.packer.model.mapper.LicenseKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("DBInitialize")
public class LicenseKeyInitalize implements CommandLineRunner {

    @Autowired
    private LicenseKeyMapper mapper;

    @Autowired
    private DataSource dataSource;

    @Value("${packer-property.licenseKey}")
    private String licenseKeyPath;

    @Override
    public void run(String... args) throws Exception {
        if(mapper.select() != null)
            return;


        BufferedReader reader = new BufferedReader(new FileReader(new File(licenseKeyPath)));
        String licenseKey = reader.readLine();
        String engineerKey = reader.readLine();
        LicenseKey entity =
                LicenseKey.builder()
                .licenseKey(licenseKey)
                .engineerKey(engineerKey)
                .build();

        mapper.insert(entity);

        reader.close();

        System.out.println("key init Success! licenseKey = "+ licenseKey + " engineerKey = " + engineerKey);

    }
}
