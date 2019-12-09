package ds;

import java.io.*;

/**
 * @author steve_chph, 2019-12-05
 * @class CharReader
 * <p>
 * 字符读取器：（1）每次读单个字符或者多个字符；（2）可以将单个字符或者多个字符回退到字符流中
 */

public class CharReader {
    private char[] charSequence;
    private int length;
    private int pointer;

    public CharReader(String source) throws IOException {
        InputStream inputStream = new FileInputStream(source);
        this.length = inputStream.available();
        this.charSequence = new char[length];
        this.pointer = 0;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.read(charSequence, 0, this.length);
    }

    public CharReader(char[] charSequence) {
        this.length = charSequence.length;
        this.charSequence = new char[this.length];
        this.pointer = 0;
        System.arraycopy(charSequence, 0, this.charSequence, 0, this.length);
    }

    public char next() {
        if (!hasNext()) return 0;
        return this.charSequence[pointer++];
    }

    public void unRead() {
        if (pointer > 0) pointer--;
    }

    public boolean hasNext() {
        return pointer < length;
    }

    public int getLength() {
        return this.length;
    }
}
