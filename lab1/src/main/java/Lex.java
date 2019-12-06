import model.Token;
import utils.CharUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lex {
    private List<Token> tokens;

    public Lex() {
        this.tokens = new ArrayList<>();
    }

    public void addToken(String lexeme, int catalog) {
        Token token = new Token(lexeme, catalog);
        this.tokens.add(token);
    }

    public void errorToken(String lexeme, int catalog, String errorMsg) {
        Token token = new Token(lexeme, catalog, errorMsg);
        this.tokens.add(token);
    }

    public Iterator<Token> getTokens() {
        return this.tokens.iterator();
    }

    public static void main(String[] args) {
        String file = "/home/steve/Documents/Projects/compiler-principle-labs/lab1/src/main/resources/test_file1.txt";
        CharReader charReader = null;
        try {
            charReader = new CharReader(file);
        } catch (IOException e) {
            System.out.println("Failed to open file!");
            e.printStackTrace();
            System.exit(1);
        }
        charReader = PreProcessor.preProcess(charReader);

        Lex lex = new Lex();
        char cur;
        StringBuilder word = new StringBuilder();
        while (charReader.hasNext()) {
            cur = charReader.next();
            word.append(cur);
            if (cur == '_' || cur == '$' || CharUtil.isAlphabet(cur)) {
                // 类名，函数名或者变量名，使用NameDFA
            } else if (cur == '\"') {
                // 字符串，使用StringDFA
            } else if (cur == '\'') {
                // 字符，使用CharacterDFA
            } else if (cur == '.') {
                // 可能有两种情况，
                // （1）以.开头的浮点数
                // （2）运算符.
            }else if (CharUtil.isDigit(cur)){
                // 可能有三种情况
                // （1）浮点数
                // （2）十进制整数
                // （3）十六进制整数
            }else {
                // 运算符或界符
            }
        }
    }
}
