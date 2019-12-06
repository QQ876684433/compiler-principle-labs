package model.dfa;

import utils.CharUtil;

/**
 * 浮点数DFAo
 */
public class FloatDFA implements DFA {
    private State state;

    FloatDFA() {
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
                if (ch == '.') {
                    this.state = State.I2;
                    return STATE_NOT_END;
                } else if (CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_NOT_END;
                } else if (ch == '.') {
                    this.state = State.I3;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I2:
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I3;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I3:
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I3;
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
        I0, I1, I2, I3
    }
}
