package com.hm.engineer_auth.service;

import com.hm.engineer_auth.application.PackerEncrypter;
import com.hm.engineer_auth.model.dto.EngineerAuthDto;
import com.hm.engineer_auth.model.entity.Engineer;
import com.hm.engineer_auth.model.mapper.EngineerMapper;
import com.hm.engineer_auth.model.network.response.EngineerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineerService {

    @Autowired
    EngineerMapper engineerMapper;

    @Autowired
    PackerEncrypter packerEncrypter;

    public void create(Engineer resource) {
        engineerMapper.save(resource);
    }

    public EngineerResponse login(EngineerAuthDto dto) throws Exception{
        dto.setId(packerEncrypter.decryptAES(packerEncrypter.getAesKey() , dto.getId()));
        dto.setPassword(packerEncrypter.decryptAES(packerEncrypter.getAesKey(), dto.getPassword()));

        Engineer engineer = engineerMapper.selectById(dto.getId());

        System.out.println(dto.getId() + " " + dto.getPassword());

        if(engineer == null || !engineer.getPassword().equals(dto.getPassword()))
            throw new Exception("회원정보가 없거나 비밀번호가 일치하지 않습니다.");

        return EngineerResponse.builder()
                .id(packerEncrypter.encryptAES(packerEncrypter.getAesKey(), engineer.getId()))
                .name(packerEncrypter.encryptAES(packerEncrypter.getAesKey(), engineer.getName()))
                .key(packerEncrypter.encryptAES(packerEncrypter.getAesKey(), engineer.getKey()))
                .email(packerEncrypter.encryptAES(packerEncrypter.getAesKey(), engineer.getEmail()))
                .build();
    }
}
