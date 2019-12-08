package object;

import ds.SortedMap;

//用于NFA状态的类
public class NFAState {
    //状态标号
    public int number = 0;
    //发出边，键为边上的值，值为下一个状态标号
    public SortedMap<Character, Integer> edgesMap;

    public NFAState(int number) {
        this.number = number;
        edgesMap = new SortedMap<>();
    }
}
