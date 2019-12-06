package model.dfa;

import utils.CharUtil;

/**
 * 字符DFAo
 */
public class CharacterDFA implements DFA {
    private State state;

    public CharacterDFA() {
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
                if (ch == '\'') {
                    this.state = State.I1;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (ch == '\'') {
                    this.state = State.I2;
                    return STATE_END;
                } else if (ch == '\\') {
                    this.state = State.I3;
                    return STATE_NOT_END;
                } else {
                    this.state = State.I1;
                    return STATE_NOT_END;
                }
            case I2:
                this.reset();
                return STATE_TERMINAL;
            case I3:
                if ("btnrf\\'".indexOf(ch) >= 0 || CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_NOT_END;
                }else {
                    this.reset();
                    return STATE_WRONG;
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
