public class Symbol {
    public static final int VT = 0;
    public static final int VN = 1;
    public static final int EPSILON = 2;
    public static final int ACCEPT = 3;

    private int type;
    private String symbol;

    public Symbol(int type, String symbol){
        this.type = type;
        this.symbol = symbol;
    }

    public int getType(){
        return this.type;
    }

    public String getSymbol(){
        return this.symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol1 = (Symbol) o;

        if (type != symbol1.type) return false;
        return symbol.equals(symbol1.symbol);
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
