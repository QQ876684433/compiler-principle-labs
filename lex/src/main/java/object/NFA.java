package object;

import ds.SortedMap;

public class NFA {
    public int start;
    public SortedMap<Integer, Integer> endStates;
    public SortedMap<Integer, NFAState> stateMap;

    public NFA() {
        start = 0;
        endStates = new SortedMap<>();
        stateMap = new SortedMap<>();
    }
}
