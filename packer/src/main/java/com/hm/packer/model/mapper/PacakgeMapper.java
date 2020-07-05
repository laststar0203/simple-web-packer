package com.hm.packer.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hm.packer.model.entity.Package;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Mapper
public interface PacakgeMapper {

    int checkPackage();

    void insert(@Param("number") int number, @Param("name") String name, @Param("version") String version,
                @Param("installed") int installed);

    int allPackageCount();

    void deleteAll();

    Package[] selectByInstalled(@Param("installed") int installed);

    void updateInstalled(@Param("number") int number, @Param("installed") int installed);
}
