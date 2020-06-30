package com.hm.packer.application.security;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hm.packer.model.dto.EngineerAuthServerMessage;
import org.springframework.stereotype.Component;


public class EngineerAuthServerMessageParser {

    private Gson gson;

    public EngineerAuthServerMessageParser(){
        gson = new Gson();
    }

    public <T> EngineerAuthServerMessage<T> parser(String json, Class<T> tClass){
        JsonObject jsonMessage = new JsonParser().parse(json).getAsJsonObject();
        return EngineerAuthServerMessage.<T>builder()
                .success(jsonMessage.get("success").getAsBoolean())
                .message(jsonMessage.get("message").getAsString())
                .data(gson.fromJson(jsonMessage.get("data"), tClass))
                .build();
    }

}
