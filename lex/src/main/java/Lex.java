import ds.CharReader;
import ds.SortedMap;
import object.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Lex {
    public static final String ILLEGAL_TOKEN = "ILLEGAL_TOKEN";

    public static void main(String[] args) throws IOException {
        String testFile = "/home/steve/Documents/Projects/compiler-principle-labs/lex/src/main/resources/test_file.txt";
        NFA nfa = SuffixExpression2NFA.transform(RE2SuffixExpression.transform(RE.loadREs()));
        DFA dfa = NFA2DFA.transform(nfa);
        List<RE> rules = RE.loadREs();
        List<Token> tokens = new LinkedList<>();
        CharReader charReader = new CharReader(testFile);
        char ch;
        int state = dfa.start;
        DFAState dfaState;
        StringBuilder builder = new StringBuilder();
        while (charReader.hasNext()) {
            ch = charReader.next();
            dfaState = dfa.stateMap.getValue(state);
            Iterator<SortedMap.Entry<Character, Integer>> iterator = dfaState.edgesMap.iterator();
            SortedMap.Entry<Character, Integer> entry;
            boolean hasFound = false;
            while (iterator.hasNext()) {
                entry = iterator.next();
                if (ch == entry.getKey()) {
                    // 找到对应的发出边
                    hasFound = true;
                    builder.append(ch);
                    state = entry.getValue();
                    break;
                }
            }
            if (!hasFound) {
                if (ch != ' ' && ch != '\n' && ch != '&')
                    charReader.unRead();
                boolean isTerminated = false;
                // 要判断当前状态是否进入了终态
                Iterator<SortedMap.Entry<Integer, Integer>> endStates = dfa.endStatesMap.iterator();
                List<Integer> endStatesList = new LinkedList<>();
                while (endStates.hasNext()) {
                    SortedMap.Entry<Integer, Integer> end = endStates.next();
                    if (end.getKey() == state) {
                        // 说明到达了终态
                        tokens.add(new Token(builder.toString(), rules.get(end.getValue()).catalog));
                        builder = new StringBuilder();
                        state = dfa.start;
                        isTerminated = true;
                        break;
                    }
                }
                if (!isTerminated && ch != ' ' && ch != '\n') {
                    builder.append(ch);
                    tokens.add(new Token(builder.toString(), ILLEGAL_TOKEN));
                }
            }
        }
        for (Token token : tokens) {
            System.out.println("<" + token.getCatalog() + ", " + token.getLexeme() + ">");
        }
    }
}
