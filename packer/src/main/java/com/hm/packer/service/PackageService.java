package com.hm.packer.service;

import com.hm.packer.model.entity.Package;
import com.hm.packer.model.mapper.PacakgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PackageService {

    @Autowired
    PacakgeMapper pacakgeMapper;

    @Transactional
    public void init(Package[] aPackages){
        pacakgeMapper.deleteAll();
        pacakgeMapper.insertAll(aPackages);
    }

    public boolean isFirst(int matchCount){
        return pacakgeMapper.allPackageCount() != matchCount;
    }

    public Package[] getClearInstallFiles() {return pacakgeMapper.selectByInstalled(1);}

}
