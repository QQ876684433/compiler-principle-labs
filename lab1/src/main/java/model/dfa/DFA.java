package model.dfa;

public interface DFA {
    int STATE_WRONG = 0;    // 当前move不能匹配且当前状态为非终态，因此此次匹配异常结束
    int STATE_END = 1;      // 当前move正常进行且后续状态为终态
    int STATE_NOT_END = 2;  // 当前move正常进行且后续状态为非终态
    int STATE_TERMINAL = 3; // 当前move不能匹配且当前状态处于终态，因此此次匹配正常结束

    void reset();

    int move(char ch);
}
