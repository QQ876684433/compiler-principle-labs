import model.Delimiter;
import model.Operator;
import model.ReservedWords;
import model.Token;
import model.DFA;
import model.DFAImpl;
import utils.CharUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lex {
    static {
        Delimiter.CATALOG_BASE = 10;
        Operator.CATALOG_BASE = Delimiter.CATALOG_BASE + Delimiter.delimiters.length;
        ReservedWords.CATALOG_BASE = Operator.CATALOG_BASE + Operator.operators.length;
        Token.CATALOG_BASE = ReservedWords.CATALOG_BASE + ReservedWords.reservedWords.length;
    }

    private List<Token> tokens;

    public Lex() {
        this.tokens = new ArrayList<>();
    }

    public void addToken(String lexeme, int catalog) {
        Token token = new Token(lexeme, catalog);
        this.tokens.add(token);
    }

    public Iterator<Token> getTokens() {
        return this.tokens.iterator();
    }

    public void parse(String file){
        CharReader charReader = null;
        try {
            charReader = new CharReader(file);
        } catch (IOException e) {
            System.out.println("Failed to open file!");
            e.printStackTrace();
            System.exit(1);
        }
        charReader = PreProcessor.preProcess(charReader);

        DFA dfa = new DFAImpl();
        char cur;
        int nextState;
        StringBuilder word = new StringBuilder();
        while (charReader.hasNext()) {
            cur = charReader.next();

            nextState = dfa.move(cur);
            if (nextState == DFA.STATE_WRONG) {
                if (CharUtil.isBlank(cur)) {
                    // 空白字符
                    if (word.length() > 0)
                        this.addToken(word.toString(), Token.TOKEN_ILLEGAL);
                    word = new StringBuilder();
                } else {
                    // 界符或者运算符
                    word.append(cur);
                    String lexeme = word.toString();
                    int catalog1 = Operator.isOperator(lexeme);
                    int catalog2 = Delimiter.isDelimiter(lexeme);
                    if (catalog1 == -1 && catalog2 == -1) {
                        // 非法界符或者运算符
                        this.addToken(lexeme, Token.TOKEN_ILLEGAL);
                        word = new StringBuilder();
                    } else if (catalog1 != -1) {
                        // 运算符
                        while (true) {
                            cur = charReader.next();
                            int c = Operator.isOperator(word.toString() + cur);
                            if (c == -1) {
                                this.addToken(word.toString(), catalog1);
                                word = new StringBuilder();
                                charReader.unRead();
                                break;
                            } else {
                                catalog1 = c;
                                word.append(cur);
                            }
                        }
                    } else {
                        // 界符
                        this.addToken(word.toString(), catalog2);
                        word = new StringBuilder();
                    }
                }
            } else if (nextState == DFA.STATE_END || nextState == DFA.STATE_NOT_END) {
                // not terminate, do nothing
                word.append(cur);
            } else {
                String lexeme = word.toString();
                if (nextState == Token.TOKEN_NAME) {
                    int catalog = ReservedWords.isReservedWord(lexeme);
                    if (catalog == ReservedWords.NOT_RESERVED_WORD) {
                        this.addToken(lexeme, nextState);
                    } else {
                        this.addToken(lexeme, catalog);
                    }
                } else {
                    this.addToken(lexeme, nextState);
                }
                charReader.unRead();
                word = new StringBuilder();
            }
        }
    }

    public void getOutput(){
        Iterator<Token> iterator = this.getTokens();
        while (iterator.hasNext()) {
            Token token = iterator.next();
            if (token.getCatalog() == Token.TOKEN_ILLEGAL) {
                System.out.println("<" + token.getCatalog() + "," + token.getLexeme() + ">\tWrong token!");
            } else {
                System.out.println("<" + token.getCatalog() + "," + token.getLexeme() + ">");
            }
        }
    }

    public static void main(String[] args) {
        String file = "/home/steve/Documents/Projects/compiler-principle-labs/lab1/src/main/resources/test_file1.txt";
        Lex lex = new Lex();
        lex.parse(file);
        lex.getOutput();
    }
}
