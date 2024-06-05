package com.cbl.cityrtgs.common.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtility {

    @Value("${narration.replace.spchar}")
    protected static String NARRATION_SPECIAL_CHAR;

    public static boolean isEmailValid(String email) {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static String validateText(String text, int maxLength) {
        String value = "RTGS";
        if (!StringUtils.isEmpty(text)) {

            //   value = text.replace("&", "and");
            value = replaceSpecialCharacters(text);
            if (value.length() > maxLength) {
                value = text.substring(0, (maxLength - 1));
            }
        }
        return value;
    }

    public static String replaceSpecialCharacters(String text) {
        java.util.Map<Character, String> replacements = new java.util.HashMap<>();
        replacements.put('@', "AT");
        replacements.put('#', "");
        replacements.put('$', "");
        replacements.put('%', "");
        replacements.put('^', "");
        replacements.put('&', "amp");
        replacements.put('*', "");
        replacements.put('(', "");
        replacements.put(')', "");
        replacements.put('_', "");
        replacements.put('+', "");
        replacements.put('{', "");
        replacements.put('}', "");
        replacements.put('[', "");
        replacements.put(']', "");
        replacements.put('|', "");
        replacements.put('\\', "");
        replacements.put(';', "");
        replacements.put(':', "");
        replacements.put('\'', "apos");
        replacements.put('\"', "quot");
        replacements.put(',', "");
        replacements.put('.', "");
        replacements.put('/', "");
        replacements.put('<', "lt");
        replacements.put('>', "gt");
        replacements.put('?', "");
        replacements.put('!', "");
        // Replace special characters in the text
        StringBuilder replacedTextBuilder = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (replacements.containsKey(c)) {
                replacedTextBuilder.append(replacements.get(c));
            } else {
                replacedTextBuilder.append(c);
            }
        }
        return replacedTextBuilder.toString();
    }


    public static boolean valid(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static String narrationValidation(String narration) {
        String outputNarration = replaceSpecialCharacters(narration);
        return outputNarration;
    }

    public static int calculateMatchingPercentage(String s1, String s2) {

        Set<String> nx = new HashSet<String>();
        Set<String> ny = new HashSet<String>();

        for (int i = 0; i < s1.length() - 1; i++) {
            char x1 = s1.charAt(i);
            char x2 = s1.charAt(i + 1);
            String tmp = "" + x1 + x2;
            nx.add(tmp);
        }
        for (int j = 0; j < s2.length() - 1; j++) {
            char y1 = s2.charAt(j);
            char y2 = s2.charAt(j + 1);
            String tmp = "" + y1 + y2;
            ny.add(tmp);
        }

        Set<String> intersection = new HashSet<String>(nx);
        intersection.retainAll(ny);
        double totcombigrams = intersection.size();

        return (int) Math.round((2 * totcombigrams) / (nx.size() + ny.size()) * 100);
    }

    public static double diceCoefficient(String s1, String s2) {
        Set<String> nx = new HashSet<String>();
        Set<String> ny = new HashSet<String>();

        for (int i = 0; i < s1.length() - 1; i++) {
            char x1 = s1.charAt(i);
            char x2 = s1.charAt(i + 1);
            String tmp = "" + x1 + x2;
            nx.add(tmp);
        }
        for (int j = 0; j < s2.length() - 1; j++) {
            char y1 = s2.charAt(j);
            char y2 = s2.charAt(j + 1);
            String tmp = "" + y1 + y2;
            ny.add(tmp);
        }

        Set<String> intersection = new HashSet<String>(nx);
        intersection.retainAll(ny);
        double totcombigrams = intersection.size();

        return (2 * totcombigrams) / (nx.size() + ny.size()) * 100;
    }

    public static String extractTTCValue(String input) {
        String pattern = "TTC:(\\d+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null; // or throw an exception, or return an empty string
    }

    public static String extractPRIValue(String input) {
        String pattern = "PRI:(\\d+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null; // or throw an exception, or return an empty string
    }

    public static String[] extractCustomDutyNarration(String input) {
        if (input == null || input.isEmpty()) {
            return new String[0];
        }
        return input.split(" ");
    }

    public static void main(String[] args) {
//        var value = calculateMatchingPercentage("Mohammed Areeq", "Md. Areeq" );
//        System.out.println("value = " + value);
//        var value2 = diceCoefficient("Golam Kabir", "Md. Golam Kabir");
//        System.out.println("value2 = " + value2);

//        String prtryString = "TTC:001,PRI:0033";
//        String extractedValue = extractTTCValue(prtryString);
//        String extractedValuePri = extractPRIValue(prtryString);
//        System.out.println("Extracted value: " + extractedValue); // Output: 001
//        System.out.println("Extracted value: " + extractedValuePri); // Output: 0033

        String ustrdString = "301 2024 891584 301790913 01710976880";
        String[] extractedValues = extractCustomDutyNarration(ustrdString);

        // Print extracted values
        for (String value : extractedValues) {
            System.out.println("Extracted value: " + value);
        }

    }

    public static int matchPercentage(String s1, String s2) {
        if (s1 != null && s2 != null) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();
            String longer = s1;
            String shorter = s2;
            if (s1.length() < s2.length()) {
                longer = s2;
                shorter = s1;
            }

            int longerLength = longer.length();
            return longerLength == 0 ? 100 : Integer.valueOf((int) ((double) (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength * 100.0));
        } else {
            return 0;
        }
    }
}
