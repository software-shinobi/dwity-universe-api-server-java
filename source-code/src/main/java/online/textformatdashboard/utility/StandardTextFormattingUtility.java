/*
 * Copyright (C) 2016 His & Hers Technologies
 *
 *
 *
 *
 *
 *
 *
 *
 */
package online.textformatdashboard.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

/**
 * Collection of String based utility functions
 *
 * @author Troy Burney
 */
public class StandardTextFormattingUtility {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StandardTextFormattingUtility.class);
    private static final String DEFAULT_STRING_PAD_CHAR = " ";
    private static final int DEFAULT_STRING_PAD_LENGTH = 10;

    public static final String TAB = "   ";
    public static final String SPACE = " ";
    public static final String NEWLINE = System.lineSeparator();
    public static final String DASH = "-";

    public static String toLeetSpeak(String x) {
        //while (sc.hasNextLine()) {
        String line = x
                .replace('o', '0')
                .replace('l', '1')
                .replace('e', '3')
                .replace('a', '4')
                .replace('t', '7')
                .replaceAll("s(?=\\s|$)", "Z")
                //     .replaceAll("\\S+", "($0)")
                .trim();
        return line;//     ps.println(line);
        //   }
    }

    /**
     * Truncates a string to a defined length
     *
     * @param content String of text to be truncated
     * @param length max length of string
     * @return truncated string
     */
    public static String truncate(final String content, final int length) {
        if (logger.isDebugEnabled()) {
            logger.debug("enter :: truncate()");
            logger.debug("param - string: {}", content);
            logger.debug("param - length: {}", length);
        }

        String truncatedString;
        if (content == null || content.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("input string was not valid. string: {}", content);
            }
            truncatedString = content;
        } else if (length < 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("trim length was not valid. trim length: {}", length);
            }
            truncatedString = content;
        } else {
            truncatedString = content.substring(0, length);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("returning: {}", truncatedString);
            logger.debug("enter :: truncate()");
        }
        return truncatedString;
    }

//    public static String getCharacterEncodingInfo(final String string) {
//        logger.debug("enter :: getCharacterEncodingInfo()");
//        logger.debug("param: {}", string);
//        logger.debug("formatted:" + WebUtil.cleanHTML(string));
//        StringBuilder buf = new StringBuilder();
//
//        buf.append("************************************").append("\n");
//        for (byte byteValue : string.getBytes()) {
//            char byteAsChar = (char) byteValue;
//
//            buf.append(" * Byte Value: ").append(StringUtil.padString(byteValue + "", 3));
//            buf.append(" Character: ").append(StringUtil.padString(byteAsChar + "", 3));
//            buf.append(" * ").append("\n");
//        }
//        buf.append("************************************").append("\n");
//
//        final String returnString = buf.toString();
//        buf = null;
//
//        logger.debug("returning: {}", returnString);
//        logger.debug("exit :: getCharacterEncodingInfo()");
//        return returnString;
//    }
    public static int countOccurence(String string, String searchString) {

        int occurenceCount = 0;
        string = string.trim();

        Pattern regularExpressionPattern = Pattern.compile(searchString);
        Matcher regularExpressionPatternMatch = regularExpressionPattern.matcher(string);

        while (regularExpressionPatternMatch.find()) {
            occurenceCount++;
        }

        regularExpressionPattern = null;
        regularExpressionPatternMatch = null;

        return occurenceCount;
    }

    public static int countOccurenceCI(String string, String searchString) {
        return countOccurence(string.toUpperCase(), searchString.toUpperCase());
    }

    public static int countOccurenceCS(String string, String searchString) {
        return countOccurence(string, searchString);
    }

    public static String padString(String string) {
        return padString(string, DEFAULT_STRING_PAD_CHAR, DEFAULT_STRING_PAD_LENGTH);
    }

    public static String padString(String string, int size) {
        return padString(string, DEFAULT_STRING_PAD_CHAR, size);
    }

    public static String padString(String inputString, String padCharacter, int size) {

        StringBuilder buf = new StringBuilder();

        if (inputString == null) {

            for (int n = 0; n < size; n++) {
                buf.append(padCharacter);
            }
            return buf.toString();
        } else if (inputString.length() >= size) {
            return inputString;
        } else {

            buf.append(inputString);

            int numCharToAdd = size - inputString.length();
            for (int n = 0; n < numCharToAdd; n++) {
                buf.append(padCharacter);
            }

            return buf.toString();
        }
    }

    public static boolean getBooleanValue(String booleanString) {

        booleanString = booleanString.trim().toUpperCase();
        if (booleanString.contains("TRUE")) {
            return true;
        } else if (booleanString.contains("FALSE")) {
            return false;
        } else if (booleanString.contains("YES")) {
            return true;
        } else if (booleanString.contains("NO")) {
            return false;
        } else if (booleanString.contains("Y")) {
            return true;
        } else if (booleanString.contains("N")) {
            return false;
        } else if (booleanString.equals("1")) {
            return true;
        } else if (booleanString.equals("0")) {
            return false;
        } else {
            return false;
        }
    }

    public static String replaceSqlParameters(String rawSqlStatement, Object[] paramList) {

        String replacedSqlStatement = rawSqlStatement.trim();
        String replaceToken = "\\?";
        int tokenCounter = 0;
        int parameterCount = paramList.length;

        java.util.regex.Pattern regularExpressionPattern = java.util.regex.Pattern.compile(replaceToken);
        java.util.regex.Matcher regularExpressionPatternMatch = regularExpressionPattern.matcher(rawSqlStatement);

        while (regularExpressionPatternMatch.find()) {
            tokenCounter++;
        }

        if (tokenCounter != parameterCount) {
            logger.error("");
            logger.error("");
            logger.error("Number Of Expected Parameters In SQL Statement Is Not Equal To The Number Of Parameters Passed");
            logger.error("SQL Token Count    : " + tokenCounter);
            logger.error("SQL Parameter Count: " + parameterCount);
            logger.error("SQL: " + rawSqlStatement);

            for (Object param : paramList) {

                logger.error("Param: " + param);
            }
            logger.error("");
            logger.error("");
            logger.error("Exiting Program, because of incorract SQL parameter(s)");
            System.exit(1234);
        }

        tokenCounter = 0;

        regularExpressionPattern = java.util.regex.Pattern.compile(replaceToken);
        regularExpressionPatternMatch = regularExpressionPattern.matcher(rawSqlStatement);

        while (regularExpressionPatternMatch.find()) {

            Object replaceObject = paramList[tokenCounter];
            replacedSqlStatement = replacedSqlStatement.replaceFirst(
                    replaceToken,
                    replaceObject.toString().replaceAll("'", "''"));

            tokenCounter++;
            replaceObject = null;
        }

        regularExpressionPattern = null;
        regularExpressionPatternMatch = null;

        return replacedSqlStatement;
    }

    public static String toBookCase(String string) {
        string = string.trim();

        StringBuilder nn = new StringBuilder();
        string = string.toLowerCase();
        for (String str : string.split(" |-")) {

            StringBuilder buf = new StringBuilder();
            buf.append(str.substring(0, 1).toUpperCase());
            buf.append(str.substring(1, str.length()).toLowerCase());

            str = buf.toString().trim();

            if (str.matches("[Ii]+")) {
                str = str.toUpperCase();
            }

            if (str.matches("[Ss][Rr][\\.]+")) {
                str = str.replaceAll("\\.", "");
            } else if (str.matches("[jJ][Rr][\\.]+")) {
                str = str.replaceAll("\\.", "");
            } else if (str.matches("[\\.\\w]*[\\.]+")) {
                str = str.toUpperCase();
            }

            nn.append(str);
            nn.append(" ");
        }

        if (string.contains("-")) {
            nn = new StringBuilder(nn.toString().trim());
            nn.setCharAt(nn.lastIndexOf(" "), '-');
        }

        return nn.toString();
    }
    private static final String WORD_SEPARATORS = " .-_/()";

    public static String toSentenceCase(final String s) {
        final StringBuilder sb = new StringBuilder(s);
        return toSentenceCase(sb).toString();
    }

    private static StringBuilder toSentenceCase(final StringBuilder sb) {
        boolean capitalizeNext = true;
        for (int i = 0; i < sb.length(); i++) {
            final char c = sb.charAt(i);
            if (c == '.') {
                capitalizeNext = true;
            } else if (capitalizeNext && !isSeparator(c)) {
                sb.setCharAt(i, Character.toTitleCase(c));
                capitalizeNext = false;
            } else if (!Character.isLowerCase(c)) {
                sb.setCharAt(i, Character.toLowerCase(c));
            }
        }
        return sb;
    }

    private static boolean isSeparator(char c) {
        return WORD_SEPARATORS.indexOf(c) >= 0;
    }

    /**
     * Converts an input string into title case, capitalizing the first
     * character of every word.
     *
     * @param s input string
     * @return string transformed into title case
     */
    public static String toTitleCase(final String s) {
        final StringBuilder sb = new StringBuilder(s);
        return toTitleCase(sb).toString();
    }

    private static StringBuilder toTitleCase(final StringBuilder sb) {
        boolean capitalizeNext = true;
        for (int i = 0; i < sb.length(); i++) {
            final char c = sb.charAt(i);
            if (isSeparator(c)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.setCharAt(i, Character.toTitleCase(c));
                capitalizeNext = false;
            } else if (!Character.isLowerCase(c)) {
                sb.setCharAt(i, Character.toLowerCase(c));
            }
        }
        return sb;
    }

}
