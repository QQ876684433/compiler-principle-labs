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
                // If X is non-terminal, and X → Y1Y2…Yk， Yj(VNVT),1 <= j <= k, then
                for (Symbol tmpSymbol : prod.getSymbols()) {
                    Set<Symbol> tmpSymbols = first(cfg, tmpSymbol);
                    symbols.addAll(tmpSymbols);
                    // judge if X→ ε is a production
                    boolean hasEpsilon = false;
                    for (Symbol s:tmpSymbols){
                        if (s.getType()==Symbol.EPSILON) {
                            hasEpsilon = true;
                            break;
                        }
                    }
                    if (!hasEpsilon) {
                        break;
                    }
                }
            }
        }

        return symbols;
    }
}
