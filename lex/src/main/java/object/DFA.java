package object;

import ds.SortedMap;

public class DFA {
    public int start;
    public SortedMap<Integer, DFAState> stateMap;
    public SortedMap<Integer, Integer> endStatesMap;

    public DFA() {
        start = 0;
        stateMap = new SortedMap<>();
        endStatesMap = new SortedMap<>();
    }
}
