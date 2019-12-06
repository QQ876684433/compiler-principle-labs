package model;

public class ReservedWords {
    public static int CATALOG_BASE;
    public static int NOT_RESERVED_WORD = -1;

    public static final String[] reservedWords = {
            "abstract", "assert",

            "boolean", "break", "byte",

            "case", "catch", "char", "class", "const", "continue",

            "default", "do", "double",

            "else", "enum", "extends",

            "final", "finally", "float", "for",

            "goto",

            "if", "implements", "import", "instanceof", "int", "interface",

            "long",

            "native", "new",

            "package", "private", "protected", "public",

            "return",

            "short", "static", "strictfp", "super", "switch", "synchronized",

            "this", "throw", "throws", "transient", "try",

            "void", "volatile",

            "while"
    };

    /**
     * check if the word given is reserved word
     *
     * @param word word to check
     * @return CATALOG_BASE + index of given word in reserved words list if it's reserved word, and NOT_RESERVED_WORD otherwise
     */
    public static int isReservedWord(String word) {
        for (int i = 0, reservedWordsLength = reservedWords.length; i < reservedWordsLength; i++) {
            String w = reservedWords[i];
            if (w.equals(word)) return CATALOG_BASE + i;
        }
        return NOT_RESERVED_WORD;
    }
}
