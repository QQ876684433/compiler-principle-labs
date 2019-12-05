public class Main {
    public static void main(String[] args) {
        String str = "(, ), {, }, [, ], ;";
        for (String ch : str.split(", ")) {
            System.out.print("\""+ch+"\"" + ",");
        }
    }
}
