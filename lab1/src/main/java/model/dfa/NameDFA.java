package model.dfa;

import utils.CharUtil;

/**
 * 类名，函数名和变量名等的DFAo
 */
public class NameDFA implements DFA {
    private State state;

    NameDFA() {
        this.reset();
    }

    @Override
    public void reset() {
        this.state = State.I0;
    }

    @Override
    public int move(char ch) {
        switch (this.state) {
            case I0:
                if (ch == '_' || ch == '$' || CharUtil.isAlphabet(ch)) {
                    this.state = State.I1;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (ch == '_' || ch == '$' || CharUtil.isAlphabet(ch) || CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_TERMINAL;
                }
            default:
                // would never reach here
                return -1;
        }
    }

    private enum State {
        I0, I1
    }
}
