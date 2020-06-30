package com.hm.engineer_auth.model.mapper;

import com.hm.engineer_auth.model.entity.Engineer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface EngineerMapper {

    Engineer selectById(@Param("id") String id);

    void save(Engineer engineer);

}
