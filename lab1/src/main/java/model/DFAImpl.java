package model;

import utils.CharUtil;

/**
 * 将NFAs合并后得到的统一的DFAo
 */
public class DFAImpl implements DFA {
    private State state;

    public DFAImpl() {
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
                    return STATE_END;
                } else if (CharUtil.isDigit(ch) && ch != '0') {
                    this.state = State.I1;
                    return STATE_END;
                } else if (ch == '0') {
                    this.state = State.I3;
                    return STATE_NOT_END;
                } else if (ch == '_' || ch == '$' || CharUtil.isAlphabet(ch)) {
                    this.state = State.I7;
                    return STATE_END;
                } else if (ch == '"') {
                    this.state = State.I8;
                    return STATE_NOT_END;
                } else if (ch == '\'') {
                    this.state = State.I12;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I1:
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_END;
                } else if (ch == '.') {
                    this.state = State.I4;
                    return STATE_END;
                } else {
                    this.reset();
                    return Token.TOKEN_DECIMAL;
                }
            case I2:
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I4;
                    return STATE_END;
                } else {
                    this.reset();
                    return Token.TOKEN_DOT;
                }
            case I3:
                if (ch == 'X' || ch == 'x') {
                    this.state = State.I5;
                    return STATE_NOT_END;
                } else if (ch == '.') {
                    this.state = State.I4;
                    return STATE_END;
                } else if (CharUtil.isDigit(ch)) {
                    this.state = State.I1;
                    return STATE_END;
                } else {
                    this.reset();
                    return Token.TOKEN_DECIMAL;
                }
            case I4:
                if (CharUtil.isDigit(ch)) {
                    this.state = State.I4;
                    return STATE_END;
                } else {
                    this.reset();
                    return Token.TOKEN_FLOAT;
                }
            case I5:
                if (CharUtil.isDigit(ch) || CharUtil.isHexAlpha(ch)) {
                    this.state = State.I6;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I6:
                if (CharUtil.isDigit(ch) || CharUtil.isHexAlpha(ch)) {
                    this.state = State.I6;
                    return STATE_END;
                } else {
                    this.reset();
                    return Token.TOKEN_HEX;
                }
            case I7:
                if (ch == '_' || ch == '$' || CharUtil.isAlphabet(ch) || CharUtil.isDigit(ch)) {
                    this.state = State.I7;
                    return STATE_END;
                } else {
                    this.reset();
                    return Token.TOKEN_NAME;
                }
            case I8:
                if (ch == '\\') {
                    this.state = State.I9;
                    return STATE_NOT_END;
                } else if (ch == '"') {
                    this.state = State.I11;
                    return STATE_END;
                } else {
                    this.state = State.I10;
                    return STATE_NOT_END;
                }
            case I9:
                if ("btnrf\\'\"".indexOf(ch) >= 0 || (CharUtil.isDigit(ch) && ch != '8' && ch != '9')) {
                    this.state = State.I10;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I10:
                if (ch == '"') {
                    this.state = State.I11;
                    return STATE_END;
                } else if (ch == '\\') {
                    this.state = State.I9;
                    return STATE_NOT_END;
                } else {
                    this.state = State.I10;
                    return STATE_NOT_END;
                }
            case I11:
                this.reset();
                return Token.TOKEN_STRING;
            case I12:
                if (ch == '\\') {
                    this.state = State.I13;
                    return STATE_NOT_END;
                } else if (ch == '\'') {
                    this.reset();
                    return STATE_WRONG;
                } else {
                    this.state = State.I14;
                    return STATE_NOT_END;
                }
            case I13:
                if ("btnrf\\'".indexOf(ch) >= 0 || (CharUtil.isDigit(ch) && ch != '8' && ch != '9')) {
                    this.state = State.I14;
                    return STATE_NOT_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I14:
                if (ch == '\'') {
                    this.state = State.I15;
                    return STATE_END;
                } else {
                    this.reset();
                    return STATE_WRONG;
                }
            case I15:
                this.reset();
                return Token.TOKEN_CHARACTER;
            default:
                // would never reach here
                return -1;
        }
    }

    private enum State {
        I0, I1, I2, I3, I4,
        I5, I6, I7, I8, I9,
        I10, I11, I12, I13, I14, I15
    }
}
