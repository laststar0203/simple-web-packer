package com.hm.packer.application.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinuxCommandExecutorTest {

    @Test
    void addCommandParam(){
        String command = "./start.sh";
        String[] param = {"a", "b", "c"};

        for(String s : param)
            command += " " + s;
        System.out.println(command);
    }
}