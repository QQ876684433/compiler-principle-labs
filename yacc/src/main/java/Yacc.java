import java.io.*;
import java.util.List;

public class Yacc {
    public static void main(String[] args) throws FileNotFoundException {
        List<Production> derivationSequence = Monitor.parse();
        String output = "yacc/src/main/resources/derivation_sequence_output.txt";
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output)));
        for (Production production : derivationSequence) {
            pw.print(production.getType() + " -> ");
            for (Symbol symbol : production.getSymbols()) {
                if (symbol.getType() == Symbol.VN) {
                    pw.print("{" + symbol.getSymbol() + "} ");
                } else {
                    pw.print(symbol.getSymbol() + " ");
                }
            }
            pw.println();
            pw.flush();
        }
        pw.close();
    }
}
