import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class CfgReader {
    public static final String START = "LANGUAGE";
    private List<Production> productions;

    public CfgReader(String prodSrc) throws FileNotFoundException {
        BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(prodSrc)));
        productions = new LinkedList<>();
        bw.lines().forEach(line -> {
            Production production;
            Symbol symbol;

            String prod = line.trim();
            String[] splits = prod.split("->");
            String type = splits[0].trim();
            // 处理 | 符号
            String[] parts = splits[1].split("\\|");
            for (String part : parts) {
                production = new Production(type);
                // 对每个部分生成一个产生式
                String[] symbols = part.trim().split(" ");
                for (String item : symbols) {
                    item = item.trim();
                    if (item.contains("{") && item.contains("}")) {
                        // 去掉{}，并将其作为VN放入产生式中
                        symbol = new Symbol(Symbol.VN, item.substring(1, item.length() - 1));
                    } else if (item.equals("ε")) {
                        symbol = new Symbol(Symbol.EPSILON, item);
                    } else {
                        // VT，对应于REs中的catalog
                        symbol = new Symbol(Symbol.VT, item);
                    }
                    production.addSymbol(symbol);
                }
                productions.add(production);
            }
        });
    }

    public List<String> getSymbols(){
        List<String> symbols = new LinkedList<>();
        for (Production production:productions){
            symbols.add(production.getType());
        }
        return symbols;
    }

    public List<Production> getProductions(){
        return this.productions;
    }

    public List<Production> getProductionByType(String type) {
        List<Production> productions = new LinkedList<>();
        for (Production production : this.productions) {
            if (production.getType().equals(type)) {
                productions.add(production);
            }
        }
        return productions;
    }

    public void  print(){
        for (Production prod : productions) {
            prod.println();
        }
    }
}
