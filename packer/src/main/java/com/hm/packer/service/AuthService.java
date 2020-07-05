package com.hm.packer.service;

import com.hm.packer.application.exception.BadEncodingException;
import com.hm.packer.application.exception.FailedEngineerServerKeyExchangeException;
import com.hm.packer.application.security.EngineerAuthServerMessageParser;
import com.hm.packer.application.security.encrypt.PackerSafeAES;
import com.hm.packer.model.dto.Engineer;
import com.hm.packer.application.security.token.Certified;
import com.hm.packer.model.dto.EngineerAuthDto;
import com.hm.packer.model.dto.EngineerAuthServerMessage;
import com.hm.packer.model.entity.LicenseKey;
import com.hm.packer.model.mapper.LicenseKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



//restTemplate 사용법 https://sooin01.tistory.com/entry/RestTemplate-%EC%82%AC%EC%9A%A9%EB%B2%95
@Service
public class AuthService {

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LicenseKeyMapper mapper;

    @Autowired
    private PackerSafeAES encryptKeyEncoder;

    @Value("${packer-property.engineer-auth-serverIP}")
    private String engineerAuthServerIP;

    public Certified onlineAuth(EngineerAuthDto dto) throws AuthenticationException {
        if(encryptKeyEncoder == null)
            throw new FailedEngineerServerKeyExchangeException("알수 없는 이유로 서버와 통신이 실패하였습니다.");

//TODO: 코드가 너무 더럽다.
        try {
            dto.setId(encryptKeyEncoder.encrypt(dto.getId()));
            dto.setPassword(encryptKeyEncoder.encrypt(dto.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
            throw new BadEncodingException("Bad Encoding");
        }

        System.out.println(dto.getId() + " " + dto.getPassword());

        EngineerAuthServerMessage<Engineer> message = new EngineerAuthServerMessageParser()
                .parser(restTemplate.postForObject(engineerAuthServerIP + "/engineer/auth", dto, String.class), Engineer.class);

        if(!message.isSuccess())
            throw new BadCredentialsException("로그인 실패하였습니다");

        Engineer engineer = message.getData();

        try {
            engineer.setKey(encryptKeyEncoder.decrypt(engineer.getKey()));
            engineer.setName(encryptKeyEncoder.decrypt(engineer.getName()));
            engineer.setEmail(encryptKeyEncoder.decrypt(engineer.getEmail()));
            engineer.setId(encryptKeyEncoder.decrypt(engineer.getId()));

        }catch (Exception e){
            e.printStackTrace();
            throw new BadEncodingException("Bad Encoding");
        }

        LicenseKey licenseKey = mapper.select();

        if(!engineer.getKey().equals(licenseKey.getEngineerKey()))
            throw new BadCredentialsException("권한이 없는 엔지니어입니다.");

        return Certified.builder()
                .engineer(engineer)
                .licenseKey(licenseKey.getLicenseKey())
                .build();
    }

    //내부 db sqlite와 비교
    public Certified offlineAuth(String key) throws AuthenticationException{

        LicenseKey licenseKey = mapper.select();
        if(!licenseKey.getLicenseKey().equals(key))
            throw new BadCredentialsException("라이센스 키가 일치하지 않습니다.");


        return Certified.builder().engineer(
                Engineer.builder()
                .id("Unknown")
                .name("Offline_User")
                .email("Unknown")
                .key("Unknown")
                .build()
        ).licenseKey(licenseKey.getLicenseKey()).build();
    }

}
