package com.auto.helper.one.common.util;

import java.io.IOException;

public class ShellUtils {

    public static boolean executeCmd(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            int status = process.waitFor();
            return status == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
