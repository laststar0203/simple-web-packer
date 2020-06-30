package com.hm.engineer_auth.model.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> Message<T> of(boolean success, String message, T data){
        return new Message<T>(success, message, data);
    }
}