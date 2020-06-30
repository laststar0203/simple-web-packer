package com.hm.engineer_auth.application.util;

public class StringUtil {

    public static String[] segment(String data, final int SIZE){
        String[] pieces = new String[(data.length() / SIZE) + 1];
        for(int i  = 0; i < pieces.length; i++){
            final int start = i * SIZE;
            final int end = (i * SIZE) + SIZE;
            pieces[i] = data.substring(start, end > data.length() ? data.length() : end);
        }
        return pieces;
    }

}
