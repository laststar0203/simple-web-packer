package com.hm.packer.application.command;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class LinuxCommandExecutor {

    public void command(String command, List<String> result) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);

        if(result != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                result.add(msg);
            }
        }

        process.waitFor();
        process.destroy();
    }

    public void command(String command, String[] param, List<String> result) throws Exception{
        for(String s : param)
            command += s + "";
        command(command, result);
    }
}
