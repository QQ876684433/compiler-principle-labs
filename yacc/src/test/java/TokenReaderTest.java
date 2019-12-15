import java.io.FileNotFoundException;

public class TokenReaderTest {
    public static void main(String[] args) throws FileNotFoundException {
        String tokenSrc = "yacc/src/main/resources/tokens.txt";
        TokenReader tokenReader = new TokenReader(tokenSrc);
        while (tokenReader.hasNext()) {
            Token token = tokenReader.nextToken();
            System.out.println(token.getCatalog() + "\t" + token.getLexeme());
        }
    }
}
