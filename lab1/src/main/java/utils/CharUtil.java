package utils;

public class CharUtil {
    public static boolean isAlphabet(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    public static boolean isDigit(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    public static boolean isHexAlpha(char ch) {
        return (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
    }
}
