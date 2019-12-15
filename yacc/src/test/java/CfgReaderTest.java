import java.io.FileNotFoundException;

public class CfgReaderTest {
    public static void main(String[] args) throws FileNotFoundException {
        String prodSrc = "yacc/src/main/resources/cfg.txt";
        CfgReader cfgReader = new CfgReader(prodSrc);
        cfgReader.print();
    }
}
