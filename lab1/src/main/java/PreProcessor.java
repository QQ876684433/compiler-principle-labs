/**
 * @author steve_chph, 2019-12-05
 * @class PreProcessor
 * <p>
 * 预处理器，主要是处理注释
 */

public class PreProcessor {
    public static CharReader preProcess(CharReader reader) {
        char[] charSequence = new char[reader.getLength()];
        int index = 0;
        char cur, next;
        while (reader.hasNext()) {
            cur = reader.next();
            if (cur == '/' && reader.hasNext()) {
                next = reader.next();
                if (next == '/') {
                    // 单行注释符号，将当前行往后的字符丢弃
                    while (reader.hasNext()) {
                        if (reader.next() == '\n') break;
                    }
                } else if (next == '*') {
                    // 多行注释符，将 */ 之前的内容丢弃，
                    // 如果找不到 */ ，则扫描过的所有字符全部丢弃
                    while (reader.hasNext()) {
                        cur = reader.next();
                        if (cur == '*' && reader.hasNext()) {
                            next = reader.next();
                            if (next == '/') {
                                break;
                            }
                        }
                    }
                } else {
                    charSequence[index++] = cur;
                    charSequence[index++] = next;
                }
            } else if (cur == 0) {
                break;
            } else {
                charSequence[index++] = cur;
            }
        }

        char[] resSequence = new char[index];
        System.arraycopy(charSequence, 0, resSequence, 0, index);
        return new CharReader(resSequence);
    }
}