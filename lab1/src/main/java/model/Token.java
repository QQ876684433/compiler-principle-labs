package model;

public class Token {
    private String lexeme;
    private int catalog;
    private String errorMsg;

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
