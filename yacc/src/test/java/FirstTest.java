import java.io.FileNotFoundException;
import java.util.Set;

public class FirstTest {
    public static void main(String[] args) throws FileNotFoundException {
        String prodSrc = "yacc/src/main/resources/cfg.txt";
        CfgReader cfgReader = new CfgReader(prodSrc);
        Set<Symbol> symbols = First.first(cfgReader, new Symbol(Symbol.VN, "RETURN"));
        for (Symbol symbol : symbols) {
            System.out.print(symbol.getSymbol() + " ");
        }
        System.out.println();
    }
}
