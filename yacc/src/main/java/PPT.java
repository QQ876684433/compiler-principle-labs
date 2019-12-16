import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PPT {
    private Map<String, Map<String, List<Symbol>>> pptMap;

    public PPT(List<String> symbols) {
        pptMap = new HashMap<>();
        for (String sym : symbols) {
            pptMap.put(sym, new HashMap<>());
        }
    }

    public void addPPTItem(String symbol, String VT, List<Symbol> production) {
        // 只需要保存右部，不需要将整个产生式保存
        this.pptMap.get(symbol).put(VT, production);
    }

    public List<Symbol> get(String symbol, String VT) {
        return this.pptMap.get(symbol).get(VT);
    }
}
