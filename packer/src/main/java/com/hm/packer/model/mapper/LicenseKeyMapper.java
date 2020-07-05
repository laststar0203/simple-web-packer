package com.hm.packer.model.mapper;

import com.hm.packer.model.entity.LicenseKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LicenseKeyMapper {

    LicenseKey select();

    void insert(LicenseKey entity);

}
