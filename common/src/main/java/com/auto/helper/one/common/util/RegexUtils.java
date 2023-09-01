package com.auto.helper.one.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private static final String TAG = RegexUtils.class.getSimpleName();

    public static boolean regexMatch(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
