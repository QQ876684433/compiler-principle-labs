import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Monitor {
    public static final String tokenSrc = "yacc/src/main/resources/tokens.txt";
    public static final Symbol $L = new Symbol(Symbol.VT, "$");
    public static final Symbol epsilon = new Symbol(Symbol.EPSILON, "ε");

    public static List<Production> parse() throws FileNotFoundException {
        PPT ppt = PPTBuilder.build();
        List<Production> derivationSequence = new LinkedList<>();
        TokenReader tokenReader = new TokenReader(tokenSrc);
        Stack<Symbol> symbolStack = new Stack<>();
        symbolStack.push($L);
        symbolStack.push(new Symbol(Symbol.VN, "LANGUAGE"));
        while (tokenReader.hasNext()) {
            Token token = tokenReader.nextToken();
            Symbol top = symbolStack.peek();
            switch (top.getType()) {
                case Symbol.VN: {
                    // X is non-terminal
                    List<Symbol> symbols = ppt.get(top.getSymbol(), token.getCatalog());
                    symbolStack.pop();
                    if (!(symbols.contains(epsilon) && symbols.size() == 1))
                        // 非ε，则将右部入栈，否则只单纯弹出栈顶符号
                        for (int i = symbols.size() - 1; i >= 0; i--) {
                            symbolStack.push(symbols.get(i));
                        }
                    // 添加推到序列
                    derivationSequence.add(new Production(top.getSymbol(), symbols));
                    // 非终止符，读头的指针不变
                    tokenReader.unGet();
                    break;
                }
                case Symbol.VT: {
                    if (token.getCatalog().equals(top.getSymbol())) {
                        if (token.getCatalog().equals("$")) {
                            // If X=a=$, the parser halts and announces
                            // successful completion of parsing;
                            System.out.println("parse successfully");
                            break;
                        } else {
                            // If X=a!=$, the parser pops X off the stack
                            // and advances the input pointer to the next
                            // input symbol
                            symbolStack.pop();
                        }
                    }
                    break;
                }
            }
        }
        return derivationSequence;
    }
}
