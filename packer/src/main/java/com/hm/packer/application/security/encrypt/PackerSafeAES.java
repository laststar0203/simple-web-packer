package com.hm.packer.application.security.encrypt;

import com.google.gson.JsonParser;
import com.hm.packer.application.exception.FailedEngineerServerKeyExchangeException;
import com.hm.packer.application.security.EngineerAuthServerMessageParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.ws.spi.http.HttpHandler;
import java.security.*;
import java.util.Base64;


public class PackerSafeAES{

    private boolean isExchange;

    private PackerRSA rsa;
    private Cipher cipher;
    private SecretKeySpec aesKey;

    public PackerSafeAES(PackerRSA rsa) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.rsa = rsa;
        this.cipher = Cipher.getInstance("AES");
    }

    public boolean connect(RestTemplate template){

        if(isExchange)
            return true;
        if(!rsa.connect(template))
            return false;

        try {
            EngineerAuthServerMessageParser parser = new EngineerAuthServerMessageParser();
//이부분을 어덯게 해야할까 aesKey : dsfsf'

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String aesKeyJSON =
                    parser.parser(template.postForObject(
                            "http://localhost:9292/packer/key",
                            new HttpEntity<>(
                                    "{\"authCode\": \"" + rsa.encrypt(rsa.getAuthCode())[0] + "\"}"
                                    , headers),
                            String.class
                    ), String.class).getData();
            this.aesKey = new SecretKeySpec(
                    rsa.decrypt(new JsonParser().parse(aesKeyJSON).getAsJsonObject().get("aesKey").getAsJsonArray().get(0).getAsString()).getBytes(), "AES");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return isExchange = true;
    }

    public String encrypt(String data) throws Exception{
        if (!isExchange)
            throw new FailedEngineerServerKeyExchangeException("The key has not been exchanged.");

        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, aesKey);

        return new String(Base64.getEncoder().encode(c.doFinal(data.getBytes("UTF-8"))));
    }

    public String decrypt(String data) throws Exception{
        if (!isExchange)
            throw new FailedEngineerServerKeyExchangeException("The key has not been exchanged.");

        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, aesKey);

        return new String(c.doFinal(Base64.getDecoder().decode(data.getBytes())), "UTF-8");
    }

    public boolean isExchange(){
        return isExchange;
    }

}
