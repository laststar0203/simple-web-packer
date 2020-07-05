package com.hm.packer.application.security.encrypt;

import com.google.gson.Gson;
import com.hm.packer.application.exception.FailedEngineerServerKeyExchangeException;
import com.hm.packer.application.security.EngineerAuthServerMessageParser;
import com.hm.packer.application.util.StringUtil;
import com.hm.packer.model.network.EngineerServerConnectResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class PackerRSA{

    private final int MAX_SIZE = 117;

    private PublicKey engineerAuthServerPublicKey;

    private PublicKey packerPublicKey;
    private PrivateKey packerPrivateKey;

    private KeyFactory keyFactory;
    private Cipher cipher;

    private String authCode;

    private boolean isExchange;

    private final String engineerAuthServerIP;

    public PackerRSA(String engineerAuthServerIP) throws NoSuchAlgorithmException, NoSuchPaddingException {
        isExchange = false;
        this.engineerAuthServerIP = engineerAuthServerIP;

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);

        KeyPair keyPair = generator.genKeyPair();

        this.keyFactory = KeyFactory.getInstance("RSA");

        this.packerPublicKey = keyPair.getPublic();
        this.packerPrivateKey = keyPair.getPrivate();

        this.cipher = Cipher.getInstance("RSA");
    }


    public boolean connect(RestTemplate template){
        if(isExchange)
            return true;

        try{
            Gson gson = new Gson();
            EngineerAuthServerMessageParser parser = new EngineerAuthServerMessageParser();

            RSAPublicKeySpec packerPublicKeySpec = getPackerPublicKeySpec();

            cipher.init(Cipher.DECRYPT_MODE, packerPrivateKey);

            EngineerServerConnectResponse engineerServerConnectResponse =
                            parser.parser(template.getForObject(
                            UriComponentsBuilder.fromHttpUrl(engineerAuthServerIP+"/packer/connect")
                                    .queryParam("clientPublicKeyModule", packerPublicKeySpec.getModulus())
                                    .queryParam("clientPublicKeyExponent", packerPublicKeySpec.getPublicExponent())
                                    .build().toUri(), String.class
                    ), EngineerServerConnectResponse.class).getData();
//나누는 크기에 기준은 자르기 전이므로 암호화후 충분히 초과될수가 있다. 이 점 기억해서 유연하게 작동될수 있도록 코드를 짜보자
            this.engineerAuthServerPublicKey = this.keyFactory.generatePublic(new RSAPublicKeySpec(
                    new BigInteger(decrypt(engineerServerConnectResponse.getPublicKeyModulus())),
                    new BigInteger(decrypt(engineerServerConnectResponse.getPublicKeyExponent()))
            ));



            this.authCode = decrypt(engineerServerConnectResponse.getAuthCode());

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return isExchange = true;
    }

    public RSAPublicKeySpec getPackerPublicKeySpec() throws InvalidKeySpecException {
        return this.keyFactory.getKeySpec(packerPublicKey, RSAPublicKeySpec.class);
    }

    public String[] encrypt(String data) throws Exception {
        if (!isExchange)
            throw new FailedEngineerServerKeyExchangeException("The key has not been exchanged.");

        String[] piece = StringUtil.segment(data, MAX_SIZE);

        cipher.init(Cipher.ENCRYPT_MODE, engineerAuthServerPublicKey);

        for(int i = 0; i < piece.length; i++){
            piece[i] = Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        }

        return piece;
    }

    public String decrypt(String data) throws Exception{

        cipher.init(Cipher.DECRYPT_MODE, packerPrivateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes())) , "UTF-8");
    }

    public String decrypt(String[] data) throws Exception{

        StringBuilder builder = new StringBuilder();

        cipher.init(Cipher.DECRYPT_MODE, packerPrivateKey);

        for (String piece: data) {
            builder.append(new String(cipher.doFinal(Base64.getDecoder().decode(piece.getBytes())), "UTF-8"));
        }
        return builder.toString();
    }

    public boolean isExchange() {
        return isExchange;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getEngineerAuthServerIP() {
        return engineerAuthServerIP;
    }
}
