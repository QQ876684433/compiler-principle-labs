import java.io.IOException;

public class MainTest {
    public static void main(String[] args) {
        String file = "/home/steve/Documents/Projects/compiler-principle-labs/lab1/src/main/resources/test_file1.txt";
        CharReader charReader = null;
        try {
            charReader = new CharReader(file);
        } catch (IOException e) {
            System.out.println("Failed to open file!");
            e.printStackTrace();
            System.exit(1);
        }
        charReader = PreProcessor.preProcess(charReader);
        while (charReader.hasNext()){
            System.out.print(charReader.next());
        }
    }
}
