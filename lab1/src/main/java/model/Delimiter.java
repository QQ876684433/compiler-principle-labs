package model;

public class Delimiter {
    private static int CATALOG_BASE;
    private static int WRONG_DELIMITER;

    public static final String[] delimiters = {
            "(", ")", "{", "}", "[", "]", ";", ","
    };

    /**
     * check if the delimiter given is legal
     *
     * @param delimiter delimiter to check
     * @return CATALOG_BASE + index of delimiter in delimiter list if it's a legal delimiter, and WRONG_DELIMITER otherwise
     */
    public static int isDelimiter(String delimiter) {
        for (int i = 0; i < delimiters.length; i++) {
            if (delimiters[i].equals(delimiter)) return CATALOG_BASE + i;
        }
        return WRONG_DELIMITER;
    }
}
