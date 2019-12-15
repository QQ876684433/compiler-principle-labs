public class Symbol {
    public static final int VT = 0;
    public static final int VN = 1;
    public static final int EPSILON = 2;

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
}
