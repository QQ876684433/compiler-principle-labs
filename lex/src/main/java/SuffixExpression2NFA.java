import ds.SortedMap;
import object.NFA;
import object.NFAState;
import object.RE;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SuffixExpression2NFA {
    private static int stateNum = 0;
    private static final int placeholder = -1;

    public static NFA transform(List<RE> reList) {
        Stack<NFA> nfaStack = new Stack<>();
        for (int i = 0; i < reList.size(); i++) {
            RE re = reList.get(i);
            String pattern = re.pattern;
            for (char ch : pattern.toCharArray()) {
                NFA firstNFA, secondNFA;
                NFAState start, end;
                switch (ch) {
                    case '|': {
                        firstNFA = nfaStack.pop();
                        secondNFA = nfaStack.pop();
                        start = new NFAState(stateNum++);
                        end = new NFAState(stateNum++);
                        firstNFA.stateMap.getValue(firstNFA.endStates.get(0).getKey()).edgesMap.add(new SortedMap.Entry<>('@', end.number));
                        secondNFA.stateMap.getValue(secondNFA.endStates.get(0).getKey()).edgesMap.add(new SortedMap.Entry<>('@', end.number));
                        start.edgesMap.add(new SortedMap.Entry<>('@', firstNFA.start));
                        start.edgesMap.add(new SortedMap.Entry<>('@', secondNFA.start));
                        secondNFA.stateMap.put(start.number, start);
                        secondNFA.stateMap.put(end.number, end);
                        secondNFA.stateMap.putAll(firstNFA.stateMap);
                        secondNFA.start = start.number;
                        secondNFA.endStates.clear();
                        secondNFA.endStates.put(end.number, placeholder);
                        nfaStack.push(secondNFA);
                        break;
                    }
                    case '*': {
                        firstNFA = nfaStack.pop();
                        start = new NFAState(stateNum++);
                        end = new NFAState(stateNum++);
                        start.edgesMap.add(new SortedMap.Entry<>('@', firstNFA.start));
                        start.edgesMap.add(new SortedMap.Entry<>('@', end.number));
                        firstNFA.stateMap.getValue(firstNFA.endStates.get(0).getKey()).edgesMap.add(new SortedMap.Entry<>('@', firstNFA.start));
                        firstNFA.stateMap.getValue(firstNFA.endStates.get(0).getKey()).edgesMap.add(new SortedMap.Entry<>('@', end.number));
                        firstNFA.start = start.number;
                        firstNFA.stateMap.put(start.number, start);
                        firstNFA.stateMap.put(end.number, end);
                        firstNFA.endStates.clear();
                        firstNFA.endStates.put(end.number, placeholder);
                        nfaStack.push(firstNFA);
                        break;
                    }
                    case '.': {
                        // todo 好像是对的
                        firstNFA = nfaStack.pop();
                        secondNFA = nfaStack.pop();
                        // secondNFA firstNFA .
                        // 因此应该将secondNFA的终态与firstNFA的初态相连
                        secondNFA.stateMap.getValue(secondNFA.endStates.get(0).getKey()).edgesMap.add(new SortedMap.Entry<>('@', firstNFA.start));
                        secondNFA.endStates.clear();
                        secondNFA.endStates.putAll(firstNFA.endStates);
                        secondNFA.stateMap.putAll(firstNFA.stateMap);
                        nfaStack.push(secondNFA);
                        break;
                    }
                    default: {
                        // todo 好像是对的
                        NFA newNFA = new NFA();
                        start = new NFAState(stateNum++);
                        end = new NFAState(stateNum++);
                        newNFA.start = start.number;
                        start.edgesMap.add(new SortedMap.Entry<>(ch, end.number));
                        newNFA.stateMap.put(start.number, start);
                        newNFA.stateMap.put(end.number, end);
                        newNFA.endStates.put(end.number, placeholder);
                        nfaStack.push(newNFA);
                        break;
                    }
                }
            }
            nfaStack.peek().endStates.get(0).setValue(i);
        }
        NFA aNFA = nfaStack.pop();
        NFA tmpNFA;
        while (!nfaStack.isEmpty()){
            NFAState start = new NFAState(stateNum++);
            tmpNFA = nfaStack.pop();
            // todo 有问题
            start.edgesMap.add(new SortedMap.Entry<>('@', aNFA.start));
            start.edgesMap.add(new SortedMap.Entry<>('@', tmpNFA.start));
            aNFA.start = start.number;
            aNFA.stateMap.put(start.number, start);
            aNFA.endStates.putAll(tmpNFA.endStates);
            aNFA.stateMap.putAll(tmpNFA.stateMap);
        }
        return aNFA;
    }
}
