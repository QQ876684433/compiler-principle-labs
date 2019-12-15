import java.util.LinkedList;
import java.util.List;

public class Production {
    private String type;
    private List<Symbol> symbols;

    public String getType() {
        return this.type;
    }

    public Production(String type) {
        this.type = type;
        symbols = new LinkedList<>();
    }

    public void addSymbol(Symbol symbol) {
        this.symbols.add(symbol);
    }

    public void println() {
        System.out.print(this.type + " -> ");
        for (Symbol symbol : symbols) {
            System.out.print(symbol.getSymbol() + ",");
        }
        System.out.println();
    }

    public List<Symbol> getSymbols(){
        return this.symbols;
    }
}
