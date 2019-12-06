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

    public static boolean isBlank(char ch) {
        return " \t\n\r\b\f\0\1\2\3\4\5\6\7".indexOf(ch) >= 0;
    }
}
