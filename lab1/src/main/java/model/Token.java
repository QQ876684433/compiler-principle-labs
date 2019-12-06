package model;

public class Token {
    /* token的catalog编码 */
    public static int CATALOG_BASE;
    public static final int TOKEN_DOT = Operator.CATALOG_BASE + Operator.isOperator(".");
    public static final int TOKEN_DECIMAL = CATALOG_BASE + 1;
    public static final int TOKEN_HEX = CATALOG_BASE + 2;
    public static final int TOKEN_FLOAT = CATALOG_BASE + 3;
    public static final int TOKEN_NAME = CATALOG_BASE + 4;
    public static final int TOKEN_STRING = CATALOG_BASE + 5;
    public static final int TOKEN_CHARACTER = CATALOG_BASE + 6;
    public static final int TOKEN_ILLEGAL = CATALOG_BASE + 7;

    private String lexeme;
    private int catalog;

    public Token(String lexeme, int catalog) {
        this.lexeme = lexeme;
        this.catalog = catalog;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getCatalog() {
        return catalog;
    }
}
