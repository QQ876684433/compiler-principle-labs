import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class TokenReader {
    private int cur;
    private List<Token> tokens;

    public TokenReader(String tokenSrc) throws FileNotFoundException {
        tokens = new LinkedList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tokenSrc)));
        br.lines().forEach(line -> {
            String tmp = line.trim();
            tmp = tmp.substring(1, tmp.length() - 1);
            String[] spits = tmp.split(",");
            tokens.add(new Token(spits[1].trim(), spits[0].trim()));
        });
        cur = 0;
    }

    public boolean hasNext() {
        return cur < tokens.size();
    }

    public Token nextToken() {
        if (hasNext()) {
            Token tmp = tokens.get(cur++);
            return new Token(tmp.getLexeme(), tmp.getCatalog());
        } else {
            return null;
        }
    }
}
