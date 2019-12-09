package object;

import ds.SortedMap;

import java.util.HashSet;
import java.util.Set;

public class DFAState {
    public int number;
    public Set<Integer> kernel;
    public Set<Integer> subset;
    public SortedMap<Character, Integer> edgesMap;

    public DFAState(int stateNum) {
        this.number = stateNum;
        kernel = new HashSet<>();
        subset = new HashSet<>();
        edgesMap = new SortedMap<>();
    }

    public void addKernel(int number){
        this.kernel.add(number);
    }

    public void addSubset(int number){
        this.subset.add(number);
    }

    public void addSubsetAll(Set<Integer> subset){
        this.subset.addAll(subset);
    }
}
