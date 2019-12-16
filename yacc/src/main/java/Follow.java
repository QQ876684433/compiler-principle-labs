import java.util.HashSet;
import java.util.Set;

public class Follow {
    public static Set<Symbol> follow(CfgReader cfg, Symbol symbol, Symbol target) {
        Set<Symbol> symbols = new HashSet<>();
        if (symbol.getSymbol().equals(CfgReader.START)) {
            symbols.add(new Symbol(Symbol.ACCEPT, "$"));
        }

        for (Production production : cfg.getProductions()) {
            if (production.containsSymbol(symbol)) {
                // If there is A → αBβ in G, then add (First(β)-) to Follow(B).
                int index = production.indexOf(symbol);
                Set<Symbol> f = First.first(cfg,
                        production.getSymbols().subList(index + 1, production.getSymbols().size()));
                Symbol epsilon = new Symbol(Symbol.EPSILON, "ε");
                boolean hasEpsilon = f.remove(epsilon);
                if (hasEpsilon) {
                    if (!(symbol.getSymbol().equals(production.getType())  || production.getType().equals(target.getSymbol())))
                        // 如果是反过来求自身的follow，则跳过
                        symbols.addAll(follow(cfg, new Symbol(Symbol.VN, production.getType()), target));
                } else {
                    symbols.addAll(f);
                }
            }
        }

        return symbols;
    }
}
