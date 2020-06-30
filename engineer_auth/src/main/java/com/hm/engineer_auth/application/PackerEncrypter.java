package com.hm.engineer_auth.application;

//문자열을 쪼개야함 공개키 BigInteger 값이 너무 크다
//Cipher.getInstance() 시간이 오래걸리므로 최대한 줄이자

import com.hm.engineer_auth.application.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

@Component
public class PackerEncrypter {

    private final int MAX_SIZE = 117;

    private KeyFactory keyFactoryRSA;

    private Cipher rsaCipher;
    private Cipher aesCipher;

    private final String AES_STRING_KEY = "HELLOHELLOHELLOH";
    private SecretKeySpec aesKey;

    public PackerEncrypter() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.rsaCipher = Cipher.getInstance("RSA");
        this.aesCipher = Cipher.getInstance("AES");

        this.keyFactoryRSA = KeyFactory.getInstance("RSA");

        this.aesKey = new SecretKeySpec(AES_STRING_KEY.getBytes(),"AES");

    }

    public KeyPair makeRSAKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);

        return generator.genKeyPair();
    }

    public String[] encryptRSA(PublicKey publicKey, String data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String[] piece = StringUtil.segment(data, MAX_SIZE);
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        for(int i = 0; i < piece.length; i++) {
            piece[i] = Base64.getEncoder().encodeToString(rsaCipher.doFinal(piece[i].getBytes()));
        }
        return piece;
    }

    public String decryptRSA(PrivateKey privateKey, String data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(rsaCipher.doFinal(Base64.getDecoder().decode(data.getBytes())), "UTF-8");
    }

    public String decryptRSA(PrivateKey privateKey, String[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);

        for(String piece : data){
            byte[] byteEncrypted = Base64.getDecoder().decode(piece.getBytes());
            builder.append(new String(rsaCipher.doFinal(byteEncrypted), "UTF-8"));
        }
        return builder.toString();
    }

    public String encryptAES(SecretKeySpec aesKey, String data) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return new String(Base64.getEncoder().encode(aesCipher.doFinal(data.getBytes("UTF-8"))));
    }

    public String decryptAES(SecretKeySpec aesKey, String data) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(aesCipher.doFinal(Base64.getDecoder().decode(data.getBytes())), "UTF-8");
    }


    public KeyFactory getKeyFactoryRSA() {
        return keyFactoryRSA;
    }

    public SecretKeySpec getAesKey() {
        return aesKey;
    }

    public String getAES_STRING_KEY() {
        return AES_STRING_KEY;
    }
}
