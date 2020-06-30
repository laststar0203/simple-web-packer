package com.hm.packer.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hm.packer.model.entity.Package;
import org.springframework.stereotype.Repository;

@Repository
public interface PacakgeMapper {

    void insertAll(@Param("list") Object[] list);

    int allPackageCount();

    void deleteAll();

    Package[] selectByInstalled(@Param("installed") int installed);

    void updateInstalled(@Param("number") int number, @Param("installed") int installed);
}
