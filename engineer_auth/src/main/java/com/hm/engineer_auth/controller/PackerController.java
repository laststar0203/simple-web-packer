package com.hm.engineer_auth.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.hm.engineer_auth.application.PackerEncrypter;
import com.hm.engineer_auth.model.network.Message;
import com.hm.engineer_auth.model.network.response.PackerConnectResponse;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;

@Controller
@RequestMapping("/packer")
public class PackerController {

    private final String RSA_PRIVATE_KEY = "RSA_PRIVATE_KEY";
    private final String RSA_CLIENT_PUBLIC_KEY = "RSA_CLIENT_PUBLIC_KEY";
    private final String CLIENT_AUTH_CODE = "CLIENT_AUTH_CODE";

    @Autowired
    private PackerEncrypter packerEncrypter;

    @GetMapping("connect")
    public ResponseEntity connect(HttpServletRequest request,
                                  @RequestParam("clientPublicKeyModule") String clientPublicKeyModule,
                                  @RequestParam("clientPublicKeyExponent") String clientPublicKeyExponent){
        try {
            HttpSession session = request.getSession();

            KeyPair keyPair = packerEncrypter.makeRSAKey();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            PublicKey clientPublicKey = packerEncrypter
                    .getKeyFactoryRSA().generatePublic(
                            new RSAPublicKeySpec(new BigInteger(clientPublicKeyModule), new BigInteger(clientPublicKeyExponent))
                    );

            String authCode = "" + (char)((int)(Math.random() * 26) + 65) +(char)((int)(Math.random() * 26) + 97);

            session.setAttribute(RSA_PRIVATE_KEY, privateKey);
            session.setAttribute(RSA_CLIENT_PUBLIC_KEY, clientPublicKey);
            session.setAttribute(CLIENT_AUTH_CODE, authCode);

            RSAPublicKeySpec publicSpec =  packerEncrypter.getKeyFactoryRSA().getKeySpec(publicKey, RSAPublicKeySpec.class);


            return ResponseEntity.ok(
                    Message.of(true, "send Public Key", PackerConnectResponse.builder()
                            .publicKeyModulus(packerEncrypter.encryptRSA(clientPublicKey, publicSpec.getModulus().toString()))
                            .publicKeyExponent(packerEncrypter.encryptRSA(clientPublicKey, publicSpec.getPublicExponent().toString())[0])
                            .authCode(packerEncrypter.encryptRSA(clientPublicKey, authCode)[0]).build()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Message.of(false, "failed create Public key", null));
        }
    }

    @PostMapping("key")
    public ResponseEntity key(HttpServletRequest request,
                              @RequestBody Map<String, Object> dataParam) {
        try{
//            System.out.println(jsonData);
//            String authCode = new JsonParser().parse(jsonData).getAsJsonObject().get("authCode").getAsString();
//            System.out.println(authCode);
            String authCode = (String) dataParam.get("authCode");
            System.out.println(authCode);

            HttpSession session = request.getSession();
            if(!packerEncrypter.decryptRSA((PrivateKey)session.getAttribute(RSA_PRIVATE_KEY), authCode)
                    .equals((String)session.getAttribute(CLIENT_AUTH_CODE)))
                throw new Exception("Unauthorized AuthCode");

            return ResponseEntity.ok(
                    Message.of(true, "send AES key",
                            "{\"aesKey\":" +
                                    new Gson().toJson(packerEncrypter.encryptRSA((PublicKey) session.getAttribute(RSA_CLIENT_PUBLIC_KEY),
                                            packerEncrypter.getAES_STRING_KEY()) , String[].class)
                                    +"}"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Message.of(false, e.getMessage(), null));
        }
    }

}
