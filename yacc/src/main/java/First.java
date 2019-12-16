import java.util.*;

public class First {
    public static Set<Symbol> first(CfgReader cfg, Symbol symbol) {
        Set<Symbol> symbols = new HashSet<>();

        if (symbol.getType() == Symbol.VT) {
            //  If X is terminal, then FIRST(X) is {X}
            symbols.add(symbol);
        } else if (symbol.getType() == Symbol.EPSILON) {
            // f X→ ε is a production
            symbols.add(symbol);
        } else {
            // 获取非终结符号symbol的所有产生式
            List<Production> productions = cfg.getProductionByType(symbol.getSymbol());
            for (Production prod : productions) {
                symbols.addAll(first(cfg, prod.getSymbols()));
            }
        }

        return symbols;
    }

    public static Set<Symbol> first(CfgReader cfg, List<Symbol> string) {
        Set<Symbol> symbols = new HashSet<>();

        for (int i = 0; i < string.size(); i++) {
            Symbol s = string.get(i);
            Set<Symbol> f = first(cfg, s);
            symbols.addAll(f);
            // judge if X→ ε is a production
            boolean hasEpsilon = false;
            for (Symbol tmp : f) {
                if (tmp.getType() == Symbol.EPSILON) {
                    hasEpsilon = true;
                    // 判断是不是最后一个符号
                    if (i != string.size() - 1) {
                        symbols.remove(new Symbol(Symbol.EPSILON, "ε"));
                    }
                    break;
                }
            }
            if (!hasEpsilon) {
                break;
            }
        }

        if (string.size() == 0) {
            symbols.add(new Symbol(Symbol.EPSILON, "ε"));
        }

        return symbols;
    }
}
