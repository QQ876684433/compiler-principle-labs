package model.dfa;

import utils.CharUtil;

/**
 * 十进制整数DFAo
 */
public class DecimalDFA implements DFA {
    private State state;

    public DecimalDFA() {
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
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (CharUtil.isDigit(ch)) {
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
