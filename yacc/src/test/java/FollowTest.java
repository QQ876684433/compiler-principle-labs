import java.io.FileNotFoundException;
import java.util.Set;

public class FollowTest {
    public static void main(String[] args) throws FileNotFoundException {
        String prodSrc = "yacc/src/main/resources/cfg.txt";
        CfgReader cfgReader = new CfgReader(prodSrc);
        for (Production production : cfgReader.getProductions()) {
            Set<Symbol> symbols = Follow.follow(cfgReader, new Symbol(Symbol.VN, production.getType()));
            System.out.print(production.getType() + " : ");
            for (Symbol symbol : symbols) {
                System.out.print(symbol.getSymbol() + " ");
            }
            System.out.println();
        }
    }
}
