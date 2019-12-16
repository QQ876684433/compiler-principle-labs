import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PPTBuilder {
    private static Symbol epsilon = new Symbol(Symbol.EPSILON, "ε");

    public static PPT build() throws FileNotFoundException {
        String prodSrc = "yacc/src/main/resources/cfg.txt";
        CfgReader cfgReader = new CfgReader(prodSrc);
        List<String> symbols = cfgReader.getSymbols();
        PPT ppt = new PPT(symbols);
        for (Production production : cfgReader.getProductions()) {
            Set<Symbol> f = First.first(cfgReader, production.getSymbols());
            boolean containsEpsilon = f.remove(epsilon);
            for (Symbol symbol : f) {
                ppt.addPPTItem(production.getType(), symbol.getSymbol(), production.getSymbols());
            }
            if (containsEpsilon) {
                // f  → , we should again
                // expand A by  if the current input symbol is
                // in FOLLOW(A)
                Set<Symbol> follows = Follow.follow(cfgReader, new Symbol(Symbol.VN, production.getType()));
                for (Symbol symbol : follows) {
                    List<Symbol> tmp = new LinkedList<>();
                    tmp.add(epsilon);
                    ppt.addPPTItem(production.getType(), symbol.getSymbol(), tmp);
                }
            }
        }
        return ppt;
    }
}
