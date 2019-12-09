import ds.SortedMap;
import object.*;

import java.io.FileNotFoundException;
import java.util.*;

public class NFA2DFA {
    private static int stateNum = 0;
    private static final int placeholder = -1;

    public static DFA transform(NFA aNFA) {
        DFA dfa = new DFA();
        DFAState dfaState = new DFAState(stateNum);
        dfaState.addKernel(aNFA.start);
        epsilonClosure(aNFA, dfaState);
        dfa.stateMap.put(dfaState.number, dfaState);
        dfa.start = dfaState.number;
        for (int i = 0; i <= stateNum; i++) {
            // 获取State(i)对应的DFA状态
            dfaState = dfa.stateMap.getValue(i);
            Map<Character, DFAState> subsets = subsetConstruct(aNFA, dfaState);
            // 接下来要查找当前的子集是否已经存在
            for (Map.Entry<Character, DFAState> subset : subsets.entrySet()) {
                DFAState candidateState = subset.getValue();
                // 通过计算核是否相同来得到两个DFAState是否相同
                // 遍历当前以及存在的所有DFAState
                boolean isExist = false;
                for (int j = 0; j <= stateNum; j++) {
                    DFAState state1 = dfa.stateMap.getValue(j);
                    if (candidateState.kernel.equals(state1.kernel)) {
                        // candidate以及存在，不需要添加新的DFAState
                        isExist = true;
                        dfaState.edgesMap.put(subset.getKey(), state1.number);
                        break;
                    }
                }
                if (!isExist) {
                    // 还要计算epsilon闭包，然后再看得到的DFAState是否以及存在
                    epsilonClosure(aNFA, candidateState);
                    // 查看DFAState是否存在
                    for (int j = 0; j <= stateNum; j++) {
                        DFAState state1 = dfa.stateMap.getValue(j);
                        if (candidateState.subset.equals(state1.subset)) {
                            isExist = true;
                            dfaState.edgesMap.put(subset.getKey(), state1.number);
                            break;
                        }
                    }

                    if (!isExist) {
                        // 要添加新的DFAState
                        // 首先分配状态编号
                        candidateState.number = ++stateNum;
                        dfa.stateMap.put(candidateState.number, candidateState);
                        dfaState.edgesMap.put(subset.getKey(), candidateState.number);
                    }
                }
            }
            // 判断新添加的DFA状态是否是终态
            Iterator<SortedMap.Entry<Character, Integer>> iterator = dfaState.edgesMap.iterator();
            while (iterator.hasNext()) {
                SortedMap.Entry<Character, Integer> entry = iterator.next();
                DFAState state = dfa.stateMap.getValue(entry.getValue());
                for (Integer number : state.subset) {
                    // 遍历当前DFAState，看它的子集是否包含aNFA的终态
                    Integer action = aNFA.endStates.getValue(number);
                    if (action != null) {
                        // 说明当前DFA状态是终态
                        // todo 会不会存在一个状态对应多个Action？
                        dfa.endStatesMap.put(entry.getValue(), action);
                    }
                }
            }
        }
        return dfa;
    }

    public static void epsilonClosure(NFA aNFA, DFAState dfaState) {
        // 将dfa状态的核拷贝到子集中
        dfaState.addSubsetAll(dfaState.kernel);
        Queue<Integer> queue = new LinkedList<>(dfaState.kernel);
        int state;
        NFAState nfaState;
        // 使用队列和宽度优先搜索查找epsilon闭包
        while (!queue.isEmpty()) {
            state = queue.poll();
            nfaState = aNFA.stateMap.getValue(state);
            Iterator<SortedMap.Entry<Character, Integer>> iterator = nfaState.edgesMap.iterator();
            while (iterator.hasNext()) {
                SortedMap.Entry<Character, Integer> entry = iterator.next();
                if (entry.getKey() == '@') {
                    queue.add(entry.getValue());
                    dfaState.addSubset(entry.getValue());
                }
            }
        }
    }

    public static Map<Character, DFAState> subsetConstruct(NFA aNFA, DFAState dfaState) {
        DFAState nextState;
        // 用来存储dfaState的发出边
        Map<Character, DFAState> subset = new LinkedHashMap<>();
        for (Integer integer : dfaState.subset) {
            // 遍历dfaState子集中的每一个NFA状态
            NFAState nfaState = aNFA.stateMap.getValue(integer);
            Iterator<SortedMap.Entry<Character, Integer>> entryIterator = nfaState.edgesMap.iterator();
            while (entryIterator.hasNext()) {
                // 遍历NDA状态的每一条边，构造子集
                SortedMap.Entry<Character, Integer> entry = entryIterator.next();
                if (entry.getKey() != '@') {
                    // 跳过epsilon边
                    if (subset.containsKey(entry.getKey())) {
                        subset.get(entry.getKey()).addKernel(entry.getValue());
                    } else {
                        nextState = new DFAState(placeholder);
                        nextState.addKernel(entry.getValue());
                        subset.put(entry.getKey(), nextState);
                    }
                }
            }
        }
        return subset;
    }

    public static void main(String[] args) throws FileNotFoundException {
        NFA nfa = SuffixExpression2NFA.transform(RE2SuffixExpression.transform(RE.loadREs()));
        DFA dfa = transform(nfa);
        List<RE> rules = RE.loadREs();
        Iterator<SortedMap.Entry<Integer, DFAState>> integerDFAStateIterator = dfa.stateMap.iterator();
        while (integerDFAStateIterator.hasNext()) {
            SortedMap.Entry<Integer, DFAState> entry = integerDFAStateIterator.next();
            System.out.print("I" + entry.getKey() + " -> ");
            if (entry.getValue().edgesMap.isEmpty()) {
                int item = dfa.endStatesMap.getValue(entry.getKey());
                System.out.println("(END, " + item + ", " + rules.get(item).catalog + ")");
            } else {
                Iterator<SortedMap.Entry<Character, Integer>> map = entry.getValue().edgesMap.iterator();
                while (map.hasNext()) {
                    SortedMap.Entry<Character, Integer> item = map.next();
                    System.out.print("(" + item.getKey() + ", I" + item.getValue() + ")  ");
                }
                System.out.println();
            }
        }
    }
}
