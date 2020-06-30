package com.hm.packer.application.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void segment(){
        String txt = "abcdefghijklmnopqrstuvwxyz";
        String[] pieces = StringUtil.segment(txt, 5);
        for(String piece : pieces)
            System.out.print(piece + " ");
        System.out.println();
    }

}