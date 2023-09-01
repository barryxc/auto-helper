package com.auto.helper.one.common.util;

public class StringUtils {


    public static boolean hasTask(String input) {
        if (input != null) {
            int startIndex = input.lastIndexOf("(");
            int endIndex = input.lastIndexOf(")");
            int middleIndex = input.lastIndexOf("/");
            if (startIndex > 0 && endIndex > 0 && middleIndex > 0) {
                String a = input.substring(startIndex + 1, middleIndex);
                String b = input.substring(middleIndex + 1, endIndex);
                return Integer.parseInt(a) < Integer.parseInt(b);
            }
        }
        return false;
    }
}
