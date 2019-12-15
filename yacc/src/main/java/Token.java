public class Token {
    private String lexeme;
    private String catalog;

    public Token(String lexeme, String catalog) {
        this.lexeme = lexeme;
        this.catalog = catalog;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getCatalog() {
        return catalog;
    }
}
