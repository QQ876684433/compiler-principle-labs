import java.util.HashSet;
import java.util.Set;

public class Follow {
    public static Set<Symbol> follow(CfgReader cfg, Symbol symbol) {
        Set<Symbol> symbols = new HashSet<>();
        if (symbol.getSymbol().equals(CfgReader.START)) {
            symbols.add(new Symbol(Symbol.ACCEPT, "$"));
        }

        for (Production production : cfg.getProductions()) {
            if (production.getSymbols().contains(symbol)) {
                // If there is A → αBβ in G, then add (First(β)-) to Follow(B).
                int index = production.getSymbols().indexOf(symbol);
                Set<Symbol> f = First.first(cfg,
                        production.getSymbols().subList(index + 1, production.getSymbols().size()));
                Symbol epsilon = new Symbol(Symbol.EPSILON, "ε");
                boolean hasEpsilon = f.contains(epsilon);
                if (hasEpsilon){
                    f.remove(epsilon);
                    symbols.addAll(follow(cfg, new Symbol(Symbol.VN, production.getType())));
                }else {
                    symbols.addAll(f);
                }
            }
        }

        return symbols;
    }
}
