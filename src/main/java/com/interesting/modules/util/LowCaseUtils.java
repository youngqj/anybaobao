package com.interesting.modules.util;


public class LowCaseUtils {

    public static String camelToUnderscore(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                output.append("_").append(Character.toLowerCase(currentChar));
            } else {
                output.append(currentChar);
            }
        }
        return output.toString();
    }
}
