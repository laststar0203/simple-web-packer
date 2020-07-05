package com.hm.packer.service;

import com.hm.packer.model.entity.Package;
import com.hm.packer.model.mapper.PacakgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class PackageService {

    @Autowired
    PacakgeMapper pacakgeMapper;


    public void init(Package[] aPackages) {

        pacakgeMapper.deleteAll();

        System.out.println(pacakgeMapper.checkPackage());

        for(Package p : aPackages) {
            pacakgeMapper.insert(p.getNumber(), p.getName(), p.getVersion(), p.getInstalled());
        }
    }

    public boolean isFirst(int matchCount){
        return pacakgeMapper.allPackageCount() != matchCount;
    }

    public Package[] getClearInstallFiles() {return pacakgeMapper.selectByInstalled(1);}

}
