package model.dfa;

import utils.CharUtil;

/**
 * 字符串的DFAo
 */
public class StringDFA implements DFA {
    private State state;

    StringDFA() {
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
                if (ch == '\"') {
                    this.state = State.I1;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (ch == '\"') {
                    this.state = State.I2;
                    return STATE_END;
                } else if (ch == '\\') {
                    this.state = State.I3;
                    return STATE_NOT_END;
                } else {
                    this.state = State.I4;
                    return STATE_NOT_END;
                }
            case I2:
                this.reset();
                return STATE_TERMINAL;
            case I3:
                if ("btnrf\\'".indexOf(ch) >= 0 || CharUtil.isDigit(ch)) {
                    this.state = State.I4;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I4:
                if (ch == '\\') {
                    this.state = State.I3;
                    return STATE_NOT_END;
                } else if (ch == '"') {
                    this.state = State.I2;
                    return STATE_END;
                }else {
                    this.state = State.I4;
                    return STATE_NOT_END;
                }
            default:
                // would never reach here
                return -1;
        }
    }

    private enum State {
        I0, I1, I2, I3, I4
    }
}
