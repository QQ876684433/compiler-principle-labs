package object;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class RE {
    public String catalog;
    public String pattern;

    public RE(String catalog, String pattern) {
        this.catalog = catalog;
        this.pattern = pattern;
    }

    public static List<RE> loadREs() throws FileNotFoundException {
        List<RE> res = new LinkedList<RE>();
        String reFile = "/home/steve/Documents/Projects/compiler-principle-labs/lex/src/main/resources/REs.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(reFile)));
        br.lines().forEach(re -> {
            String pattern = re.replace("9a", "9|a").replace("zA", "z|A");
            if (re.indexOf('[') >= 0 && re.indexOf(']') >= 0) {
                pattern = pattern.replace('[', '(').replace(']', ')');
                StringBuilder tmp = new StringBuilder();
                if (pattern.contains("a-z")) {
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        tmp.append(ch);
                        if (ch != 'z') tmp.append('|');
                    }
                    pattern = pattern.replace("a-z", tmp.toString());
                    tmp = new StringBuilder();
                }
                if (pattern.contains("A-Z")) {
                    for (char ch = 'A'; ch <= 'Z'; ch++) {
                        tmp.append(ch);
                        if (ch != 'Z') tmp.append('|');
                    }
                    pattern = pattern.replace("A-Z", tmp.toString());
                    tmp = new StringBuilder();
                }
                if (pattern.contains("0-9")) {
                    for (char ch = '0'; ch <= '9'; ch++) {
                        tmp.append(ch);
                        if (ch != '9') tmp.append('|');
                    }
                    pattern = pattern.replace("0-9", tmp.toString());
                }
            }
            String[] splits = pattern.split("->");
            res.add(new RE(splits[0].trim(), splits[1].trim()));
        });
        return res;
    }
}
