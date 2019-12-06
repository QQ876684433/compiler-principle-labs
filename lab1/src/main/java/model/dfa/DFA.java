package model.dfa;

import model.Delimiter;
import model.Operator;
import model.ReservedWords;

public interface DFA {
    int STATE_WRONG = 0;    // 当前move不能匹配且当前状态为非终态，因此此次匹配异常结束
    int STATE_END = 1;      // 当前move正常进行且后续状态为终态
    int STATE_NOT_END = 2;  // 当前move正常进行且后续状态为非终态
    int STATE_TERMINAL = 3; // 当前move不能匹配且当前状态处于终态，因此此次匹配正常结束

    /* token的catalog编码 */
    int CATALOG_BASE = Operator.CATALOG_BASE + Delimiter.CATALOG_BASE + ReservedWords.CATALOG_BASE;
    int TOKEN_DOT = Operator.CATALOG_BASE + Operator.isOperator(".");
    int TOKEN_DECIMAL = CATALOG_BASE + 1;
    int TOKEN_HEX = CATALOG_BASE + 2;
    int TOKEN_FLOAT = CATALOG_BASE + 3;
    int TOKEN_NAME = CATALOG_BASE + 4;
    int TOKEN_STRING = CATALOG_BASE + 5;
    int TOKEN_CHARACTER = CATALOG_BASE + 6;

    void reset();

    int move(char ch);
}
