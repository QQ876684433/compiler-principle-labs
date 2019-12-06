package model;

public class Operator {
    public static int CATALOG_BASE;
    public static int WRONG_OPERATOR = -1;

    public static final String[] operators = {
            "++", "--", "+", "-", "~", "!", "*", "/", "%",
            "<<", ">>", ">>>", "<", ">", "<=", ">=", "==", "!=",
            "&", "^", "|", "&&", "||",
            "=", "+=", "-=", "*=", "/=", "%=", "&=", "^=", "|=",
            "<<=", ">>=", ">>>=", ".", "@", "?", ":"
    };

    /**
     * check if the word given is legal
     *
     * @param op op to check
     * @return CATALOG_BASE + index of op in operators list if it's a legal operator, and WRONG_OPERATOR otherwise
     */
    public static int isOperator(String op) {
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].equals(op)) return CATALOG_BASE + i;
        }
        return WRONG_OPERATOR;
    }
}
