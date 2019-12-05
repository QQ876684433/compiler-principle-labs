package model.dfa;

/**
 * 十六进制DFAo
 */
public class HexDFA implements DFA {
    private State state;

    public HexDFA() {
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
                if (ch == '0') {
                    this.state = State.I1;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (ch == 'x' || ch == 'X') {
                    this.state = State.I2;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I2:
                if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F')) {
                    this.state = State.I3;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I3:
                if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F')) {
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
