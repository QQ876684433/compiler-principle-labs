package model.dfa;

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
        switch (this.state){
            default:
                // would never reach here
                return -1;
        }
    }

    private enum State {
        I0, I1, I2, I3, I4,
        I5, I6, I7, I8, I9,
        I10, I11, I12, I13
    }
}
