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

    public Production(String type, List<Symbol> symbols){
        this.type = type;
        this.symbols = symbols;
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

    public List<Symbol> getSymbols() {
        return this.symbols;
    }

    public boolean containsSymbol(Symbol symbol) {
        return indexOf(symbol) >= 0;
    }

    public int indexOf(Symbol symbol) {
        for (int i = 0; i < symbols.size(); i++) {
            Symbol s = symbols.get(i);
            if (s.equals(symbol)) {
                return i;
            }
        }
        return -1;
    }
}
